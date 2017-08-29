package util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.util.KeyManagerUtils;
import org.apache.commons.vfs2.*;
import org.apache.commons.vfs2.provider.ftp.FtpFileSystemConfigBuilder;
import org.apache.commons.vfs2.provider.ftp.FtpFileType;
import org.apache.commons.vfs2.provider.ftps.FtpsDataChannelProtectionLevel;
import org.apache.commons.vfs2.provider.ftps.FtpsFileSystemConfigBuilder;
import org.apache.commons.vfs2.provider.ftps.FtpsMode;
import org.apache.commons.vfs2.provider.sftp.IdentityInfo;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Usage: <b> Provide file operation on Apache VFS </b>
 * Support protocols: ftp, ftps(only password), sftp(password or RSA key)
 * Functions: All allowed upload/download(with selector)
 * Instantiate: by Builder Pattern
 * Exception: client must handle all exception itself (to help provide detailed info)
 *
 * <pre>
 *     VFSProxy proxy = new VFSProxy.VFSProxyBuilder(VFSProxy.Protocol.SFTP, "Sftp server", 22, "authUser")
                .setPassword("password")
                .setRootPath("/")
                .build();

        String[] fileObjects = proxy.listFiles("/");
 * </pre>
 * @author Jingyi.Yang
 *         Date 2017/7/19
 **/
public class VFSProxy {
    private final Protocol protocol;

    private final String host;
    private final int port;
    private final String username;
    private final String password;

    private final File authKeyFile;
    private final byte[] authKeyPwd;
    private final String rootPath;

    private FileSystemManager manager;

    private VFSProxy(VFSProxyBuilder builder) {
        this.protocol = builder.protocol;
        this.host = builder.host;
        this.port = builder.port;
        this.username = builder.username;
        this.password = builder.password;
        this.authKeyFile = builder.authKeyFile;
        this.authKeyPwd = DataUtil.str2byte(builder.authKeyPwd);
        this.rootPath = builder.rootPath;
    }

    /**
     * Try authenticate
     *
     * @return true if connected, if any exception, false
     * @throws FileSystemException if any wrong in configuration, authentication or network
     */
    public boolean isAuthorized() throws FileSystemException {
        try (FileObject object = authenticate()) {
            return object != null;
        }
    }

    /**
     * Configure and connect to server
     *
     * @return FileObject on remote server, or null if failed
     * @throws FileSystemException if any wrong in configuration, authentication or network
     */
    private FileObject authenticate() throws FileSystemException {
        //  validate params, by entityChecker
        if (validateConfig()) {
            manager = VFS.getManager();
            return manager.resolveFile(protocol.buildUrl(username, password, host, port, rootPath),
                    buildConnectParamByProtocol());
        }
        return null;
    }

    /**
     * Check if properties are valid
     *
     * @return true if valid, exception if invalid
     */
    private boolean validateConfig() {
        List<String> errors = new EntityChecker.CheckerBuilder().bind(this)
                .check("username", EntityChecker.NOT_BLANK)
                .check("username", EntityChecker.ASCII_STRING)
                .check("host", EntityChecker.NOT_BLANK)
                .check("host", EntityChecker.ASCII_STRING)
                .check("port", EntityChecker.DIGITS)
                .check("rootPath", EntityChecker.FILE_PATH_LINUX)
                .validate();
        if (CollectionUtils.isEmpty(errors)) {
            if (StringUtils.isNotBlank(password)) {     // all protocols may use pwd
                return true;
            } else if (protocol == Protocol.SFTP && null != authKeyFile) { // sftp may use key file
                return true;
            } else {
                errors.add("One of password or auth key file must be configured");
            }
        }
        throw new IllegalArgumentException("Invalid VFSProxy properties: " + Arrays.toString(errors.toArray()));
    }

    /**
     * Configure VFS and build url by protocol
     *
     * @return connect param, Pair.of(URL, Options)
     * @throws FileSystemException if auth key invalid
     */
    private FileSystemOptions buildConnectParamByProtocol() throws FileSystemException {
        FileSystemOptions options = new FileSystemOptions();

        /* Common configurations for all protocols, please test compatibility when adding new protocol */
        FtpFileSystemConfigBuilder.getInstance().setFileType(options, FtpFileType.BINARY);
        FtpFileSystemConfigBuilder.getInstance().setPassiveMode(options, true);
        FtpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(options, true);

        /* Timeout setting for ftp */
        FtpFileSystemConfigBuilder.getInstance().setSoTimeout(options, 60 * 1000);
        FtpFileSystemConfigBuilder.getInstance().setDataTimeout(options, 240 * 1000);       // in case too many files
        FtpFileSystemConfigBuilder.getInstance().setConnectTimeout(options, 60 * 1000);

        /* Special configurations for each protocol */
        switch (protocol) {
            case SFTP:
                SftpFileSystemConfigBuilder.getInstance().setTimeout(options, 60 * 1000);
                if (StringUtils.isBlank(password)) {        // Use key file as authentication
                    SftpFileSystemConfigBuilder.getInstance().setIdentityInfo(options, new IdentityInfo(authKeyFile, authKeyPwd));
                }
                break;
            case FTP:     // no special options for ftp
                break;
            case FTPS:      // ftps requires special encryption
                // MIND!! do NOT support Session ssl reuse
                FtpsFileSystemConfigBuilder.getInstance().setFtpsMode(options, FtpsMode.EXPLICIT);
                FtpsFileSystemConfigBuilder.getInstance().setDataChannelProtectionLevel(options,
                        FtpsDataChannelProtectionLevel.P);
                // if use rsa (untested):
                if (StringUtils.isBlank(password)) {
                    try {
                        FtpsFileSystemConfigBuilder.getInstance().setKeyManager(options,
                                KeyManagerUtils.createClientKeyManager(authKeyFile, new String(authKeyPwd)));
                    } catch (IOException | GeneralSecurityException e) {
                        throw new FileSystemException("Unable to set auth key file for ftps", e);
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid protocol");
        }
        return options;
    }

    /**
     * Download all files from server path to local copy
     *
     * @param serverPath relative path to root of server
     * @param localPath  absolute path to save files, format in file URI scheme, eg. file:///sDrives[|sFile]
     * @return list of local files
     * @throws FileSystemException if connection or download error
     */
    public List<File> downloadAllFiles(String serverPath, String localPath) throws FileSystemException {
        return downloadFiles(serverPath, localPath, Selectors.SELECT_ALL);
    }

    /**
     * Download files from server path to local copy, if matched selector
     *
     * @param serverPath relative path to root of server
     * @param localPath  absolute path to save files, format in file URI scheme, eg. file:///sDrives[|sFile]
     * @param selector   selector to filter files on server (some selector failed on VFS, why ?)
     * @return list of local files
     * @throws FileSystemException if connection or download error
     */
    public List<File> downloadFiles(String serverPath, String localPath, FileSelector selector) throws FileSystemException {
        assert selector != null;
        List<File> result = new ArrayList<>();

        try (FileObject remoteFile = authenticate();
             FileObject localFile = manager.resolveFile(localPath)) {
            localFile.copyFrom(remoteFile.resolveFile(serverPath), selector);
            //localFile.forEach(fileObject -> log.info(fileObject.getName().getBaseName()));

            for (FileObject fo : localFile.getChildren()) {
                result.add(new File(fo.getURL().getFile()));
            }
        } catch (FileSystemException e) {
            throw new FileSystemException("Download files to local failed", e);
        }
        return result;
    }

    /**
     * List all fileObject of remote path, only for Test
     *
     * @param serverPath relative path to remote server root
     * @return file name array, or empty array if failed
     * @throws FileSystemException if connection or list children error
     */
    public String[] listFiles(String serverPath) throws FileSystemException {
        try (FileObject remoteFile = authenticate()) {
            return Arrays.stream(remoteFile.resolveFile(serverPath).getChildren())
                    .map(f -> f.getName().getPath()).toArray(String[]::new);
        } catch (FileSystemException | NullPointerException e) {
            throw new FileSystemException("List remote files failed", e);
        }
    }

    /**
     * Upload files to serverPath, overwrite if exists, create if parent dir not exist
     * MIND!! Too many / large files may cause timeout
     *
     * @param serverPath relative path to root of server
     * @param files      upload file list
     * @return true if all succeed
     * @throws FileSystemException if connection or upload error
     */
    public boolean uploadFile(String serverPath, List<File> files) throws FileSystemException {
        try (FileObject remoteFile = authenticate()) {
            for (File file : files) {       // use for-loop help exception throw
                try (FileObject localFile = manager.toFileObject(file)) {
                    remoteFile.resolveFile(serverPath + "/" + file.getName())
                            .copyFrom(localFile, Selectors.SELECT_SELF);
                }
            }
            return true;
        } catch (FileSystemException e) {
            throw new FileSystemException("Unable to upload files", e);
        }
    }

    /**
     * Get working directory(relative to server root path)
     * (useless, cause all path are controlled by parameters from client, client should know)
     *
     * @return working dir
     * @throws FileSystemException if connection or parse error
     */
    String getWorkingDir() throws FileSystemException {
        try (FileObject remoteFile = authenticate()) {
            return remoteFile.getURL().toURI().getPath();
        } catch (FileSystemException | URISyntaxException | NullPointerException e) {
            throw new FileSystemException("Get working directory failed", e);
        }
    }

    /* All protocols supported by VFSProxy */
    public enum Protocol {
        SFTP {
            @Override
            public String buildUrl(String username, String password, String host, int port, String rootPath) {
                // Use password or use key file
                return StringUtils.isNotBlank(password) ?
                        String.format("%s://%s:%s@%s:%s/%s", this.name().toLowerCase(), username, password, host, port, rootPath)
                        :
                        String.format("%s://%s@%s:%s/%s", this.name().toLowerCase(), username, host, port, rootPath);
            }
        },
        FTP {
            @Override
            public String buildUrl(String username, String password, String host, int port, String rootPath) {
                return String.format("ftp://%s:%s@%s:%s/%s", username, password, host, port, rootPath);
            }
        },
        FTPS {
            @Override
            public String buildUrl(String username, String password, String host, int port, String rootPath) {
                return String.format("ftps://%s:%s@%s:%s/%s", username, password, host, port, rootPath);
            }
        };

        public abstract String buildUrl(String username, String password, String host, int port, String rootPath);
    }

    public static class VFSProxyBuilder {
        private final VFSProxy.Protocol protocol;
        private final String host;
        private final int port;
        private final String username;
        private String password;
        private File authKeyFile;
        private String authKeyPwd;
        private String rootPath;

        public VFSProxyBuilder(VFSProxy.Protocol protocol, String host, int port, String username) {
            this.protocol = protocol;
            this.host = host;
            this.port = port;
            this.username = username;
        }

        public VFSProxyBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public VFSProxyBuilder setAuthKeyFile(File authKeyFile) {
            this.authKeyFile = authKeyFile;
            return this;
        }

        public VFSProxyBuilder setAuthKeyPwd(String authKeyPwd) {
            this.authKeyPwd = authKeyPwd;        // convert pwd to byte array
            return this;
        }

        public VFSProxyBuilder setRootPath(String rootPath) {
            this.rootPath = rootPath;
            return this;
        }

        public VFSProxy build() {
            return new VFSProxy(this);
        }
    }
}
