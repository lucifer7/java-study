package util.http;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Usage: <b> </b>
 *
 * @author Jingyi.Yang
 *         Date 2016/9/27
 **/
public class GoogleHttpAuthUtil {

    public static String getHtmlContentByGoogleAuthCode(String url, String authCode) {
        HttpClient client = new HttpClient();
        GetMethod method = new GetMethod(url);
        if (authCode != null) {
            method.addRequestHeader("Authorization", "GoogleLogin auth=" + authCode);
//            method.addRequestHeader("Authorization", "GoogleLogin auth=" + authCode);
//            method.addRequestHeader("accept", "*/*");
        }
        StringBuffer data = new StringBuffer();
//        client.setTimeout(20000);
        InputStreamReader in = null;
        BufferedReader reader = null;
        try {
            client.executeMethod(method);
            final int statusCode = method.getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                in = new InputStreamReader(method.getResponseBodyAsStream(), "UTF-8");
                reader = new BufferedReader(in);
                String line;
                while ((line = reader.readLine()) != null) {
                    data.append(line).append(System.getProperty("line.separator"));
                }

            }else{
                data.append("AutoCode:" + authCode ).append("\n\r");
                data.append("HTTP:" + statusCode );
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }finally {
            try {
                method.releaseConnection();
                if(in!=null)
                    in.close();
                if(reader!=null)
                    reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return data.toString();
    }

    public static boolean postHtmlContentByGoogleAuthCode(String url, String authCode, String xmlContent) {
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(url);
        boolean isSuccess = false;
        method.setRequestHeader("Content-Type", "application/xml");
        if (authCode != null) {
            method.addRequestHeader("Authorization", "GoogleLogin auth=" + authCode);
        }
        StringBuffer data = new StringBuffer();
        InputStreamReader in = null;
        BufferedReader reader = null;
        try {
            if (StringUtils.isNotBlank(xmlContent)) {
                RequestEntity entity = new StringRequestEntity(xmlContent, "application/xml",
                        "UTF-8");
                method.setRequestEntity(entity);
            }
            client.executeMethod(method);
            final int statusCode = method.getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                isSuccess = true;
                in = new InputStreamReader(method.getResponseBodyAsStream(), "UTF-8");
                reader = new BufferedReader(in);
                String line;
                while ((line = reader.readLine()) != null) {
                    data.append(line).append(System.getProperty("line.separator"));
                }

            } else {
                data.append("AutoCode:" + authCode).append("\n\r");
                data.append("HTTP:" + statusCode);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            try {
                method.releaseConnection();
                if (in != null)
                    in.close();
                if (reader != null)
                    reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return isSuccess;
    }

    private static String getAutoCodeByURL(String authCodeURL) {
        String authCode = null;
        String html = getHtmlContentByGoogleAuthCode(authCodeURL, null);
        if (html != null) {
            String[] responseLines = html.split("\n");
            for (String row : responseLines) {
                if (row.startsWith("Auth")) {
                    authCode = row.split("=")[1];
                }
            }
        }
        return authCode;
    }

}
