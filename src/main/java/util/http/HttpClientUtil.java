package util.http;

import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/8/10
 **/
@Log4j
public class HttpClientUtil {

    private static String _doHttpPost(String url, HttpEntity entity, String token) {
        HttpPut putRequest = new HttpPut(url);
        putRequest.setHeader("Authorization", "Bearer " + token);
        putRequest.setHeader(HTTP.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());
        putRequest.setEntity(entity);

        DefaultHttpClient client = new DefaultHttpClient();
        try {
            HttpResponse response = client.execute(putRequest);
            if (null != response) {
                HttpEntity responseEntity = response.getEntity();
                if (null != responseEntity) {
                    return EntityUtils.toString(responseEntity, Consts.UTF_8);
                }
            }
        } catch (IOException e) {
            log.error(String.format("Failed to get response from %s", url), e);
        }

        return null;
    }

    /**
     * Execute http put request with nameValuePair list, return response text
     * @param url
     * @param params
     * @param token
     * @return
     */
    public static String doHttpPut(String url, List<NameValuePair> params, String token) {
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
            return _doHttpPost(url, entity, token);
        } catch (UnsupportedEncodingException e) {
            log.error(String.format("Invalid params for http PUT request with url=%s", url), e);
        }
        return null;
    }

    /**
     * Execute http put request with parameterMap, return response text
     * @param url
     * @param parameterMap
     * @param token
     * @return
     */
    public static String doHttpPut(String url, Map<String, String> parameterMap, String token) {
        // 1. Convert parameterMap to nameValuePairList
        List<NameValuePair> params = Lists.newArrayListWithCapacity(parameterMap.size());
        for (Map.Entry<String, String> param : parameterMap.entrySet()) {
            params.add(new BasicNameValuePair(param.getKey(), param.getValue()));
        }

        // 2. Execute PUT
        return doHttpPut(url, params, token);
    }

    /**
     * Execute http put request with Json, return response text
     * @param url
     * @param json
     * @param token
     * @return
     */
    public static String doHttpPutJson(String url, String json, String token) {
        try {
            StringEntity entity = new StringEntity(json);
            entity.setContentType(ContentType.APPLICATION_JSON.toString());
            return _doHttpPost(url, entity, token);
        } catch (UnsupportedEncodingException e) {
            log.error(String.format("Invalid params for http PUT request with url=%s", url), e);
        }
        return null;
    }
}
