package network.server;

import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Arrays;

/**
 * Usage: <b> Secure server over ssl </b>
 * generate key by: <b>keytool -genkey -alias ourstore -keystore jnp4e.keys</b>
 * test server by: <b>echo 'GET /index HTTP/1.0' | openssl s_client -connect IP:PORT</b>
 *
 * @author lucifer
 *         Date 2017-12-23
 *         Device Aurora R5
 */
@Slf4j
public class SSlServer {
    private static final int PORT = 8091;
    private static final String ALGORITHM = "SSL";
    private static final String FACTORY = "SunX509";
    private static final String STORE = "JKS";
    private static final String JNP4E_KEYS = "jnp4e.keys";      // Path to key file

    public static void main(String[] args) {
        try {
            SSLContext context = SSLContext.getInstance(ALGORITHM);
            KeyManagerFactory factory = KeyManagerFactory.getInstance(FACTORY);
            KeyStore store = KeyStore.getInstance(STORE);

            //char[] password = new char[]{'1', '2', '3', '4', '5', '6'};       // If don't want to run in console
            char[] password = System.console().readPassword();
            store.load(new FileInputStream(JNP4E_KEYS), password);
            factory.init(store, password);
            context.init(factory.getKeyManagers(), null, null);

            Arrays.fill(password, '0');     // Clean password from memory

            SSLServerSocketFactory serverSocketFactory = context.getServerSocketFactory();
            SSLServerSocket serverSocket = (SSLServerSocket) serverSocketFactory.createServerSocket(PORT);

            String[] supported = serverSocket.getSupportedCipherSuites();
            String[] anonCipherSuitesSupported = Arrays.stream(supported)
                    .filter(i -> i.contains("_anon_"))
                    .toArray(String[]::new);

            String[] oldEnabled = serverSocket.getEnabledCipherSuites();
            String[] newEnabled = new String[oldEnabled.length + anonCipherSuitesSupported.length];
            System.arraycopy(oldEnabled, 0, newEnabled, 0, oldEnabled.length);
            System.arraycopy(anonCipherSuitesSupported, 0, newEnabled, oldEnabled.length,
                    anonCipherSuitesSupported.length);
            
            serverSocket.setEnabledCipherSuites(newEnabled);

            // Security prepared
            while (true) {
                try (Socket connection = serverSocket.accept();
                     InputStream in = connection.getInputStream()) {
                    int c;
                    while ((c = in.read()) != -1) {
                        System.out.write(c);
                    }
                } catch (IOException e) {
                    log.error("Read input error", e);
                }
            }
        } catch (NoSuchAlgorithmException | KeyStoreException | CertificateException | IOException | UnrecoverableKeyException | KeyManagementException e) {
            log.error("Failed to start server", e);
        }
    }
}
