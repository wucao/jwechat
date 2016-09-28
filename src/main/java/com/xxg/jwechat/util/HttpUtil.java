package com.xxg.jwechat.util;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by wucao on 16/9/28.
 */
public class HttpUtil {

    /**
     * GET请求
     * @param url URL
     * @param charset 响应Body编码
     * @return 响应内容
     * @throws IOException
     */
    public static String get(String url, String charset) throws IOException {
        OutputStream output = null;
        InputStream input = null;
        URLConnection connection = new URL(url).openConnection();
        connection.setConnectTimeout(30000);
        connection.setReadTimeout(30000);
        try {
            input = connection.getInputStream();
            return IOUtils.toString(input, charset);
        } finally {
            IOUtils.closeQuietly(input);
            IOUtils.closeQuietly(output);
        }
    }

    /**
     * GET请求
     * @param url URL
     * @return 响应内容
     * @throws IOException
     */
    public static String get(String url) throws IOException {
        return get(url, "UTF-8");
    }
}
