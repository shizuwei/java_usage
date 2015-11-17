/**
 * Baijiahulian.com Inc. Copyright (c) 2014-2015 All Rights Reserved.
 */

package shizuwei.test.java;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @title HttpClientUsage
 * @desc TODO
 * @author shizuwei
 * @date 2015年11月13日
 * @version 1.0
 */

public class HttpClientUsage {

    private static final Logger log = LoggerFactory.getLogger(HttpClientUsage.class);

    public void test1(String url) throws Exception {
        // default httpclient
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        try {
            HttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.print(statusCode);
            HttpEntity entity = response.getEntity();
            String content = EntityUtils.toString(entity, "UTF-8");
            System.out.println(content);

            content.matches("<a>");

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public static void main(String[] args) throws Exception {
        HttpClientUsage usage = HttpClientUsage.class.newInstance();
        usage.test1("http://www.cnblogs.com/");
    }

}
