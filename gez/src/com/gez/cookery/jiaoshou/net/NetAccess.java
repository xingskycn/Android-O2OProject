package com.gez.cookery.jiaoshou.net;

import com.gez.cookery.jiaoshou.model.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

public final class NetAccess {
    public static final int HTTP_TIMEOUT = 30000;
    //private static final String BASE_URL = "http://203.171.224.100/api/";

    public static DefaultHttpClient getHttpClient() {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), HTTP_TIMEOUT);
        HttpConnectionParams.setSoTimeout(httpClient.getParams(), HTTP_TIMEOUT);
        ConnManagerParams.setTimeout(httpClient.getParams(), HTTP_TIMEOUT);
        return httpClient;
    }

    private static String request(HttpClient client, String url, List<BasicNameValuePair> data) throws IOException {
        HttpUriRequest req;
        if (null != data && data.size() > 0) {
            HttpPost post = new HttpPost(url);
            post.setEntity(new UrlEncodedFormEntity(data, HTTP.UTF_8));
            req = post;
        } else {
            req = new HttpGet(url);
        }
        String s = EntityUtils.toString(client.execute(req).getEntity());
        System.out.println(s);
        return s.toLowerCase();
    }
}
