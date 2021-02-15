package com.raiden.utils;



import com.raiden.model.URLInfo;

import java.util.HashMap;
import java.util.Map;

public final class UrlUtils {

    private UrlUtils(){}

    /**
     * 解析URL 获取其中的属性
     * @param url
     * @return
     */
    public static URLInfo parseUrl(String url) {
        if (url == null || (url = url.trim()).length() == 0) {
            throw new IllegalArgumentException("url == null");
        }
        String host = null;
        int port = 0;
        String path = null;
        Map<String, String> parameters = null;
        int i = url.indexOf("?"); // seperator between body and parameters
        if (i > -1) {
            String[] parts = url.substring(i + 1).split("\\&");
            parameters = new HashMap<String, String>();
            for (String part : parts) {
                part = part.trim();
                if (part.length() > 0) {
                    int j = part.indexOf('=');
                    if (j >= 0) {
                        parameters.put(part.substring(0, j), part.substring(j + 1));
                    } else {
                        parameters.put(part, part);
                    }
                }
            }
            url = url.substring(0, i);
        }
        i = url.indexOf("://");
        if (i > -1) {
            if (i == 0) throw new IllegalStateException("url missing protocol: \"" + url + "\"");
            url = url.substring(i + 3);
        } else {
            // case: file:/path/to/file.txt
            i = url.indexOf(":/");
            if (i > -1) {
                if (i == 0) throw new IllegalStateException("url missing protocol: \"" + url + "\"");
                url = url.substring(i + 1);
            }
        }
        i = url.indexOf("/");
        if (i > -1) {
            path = url.substring(i + 1);
            url = url.substring(0, i);
        }
        i = url.lastIndexOf("@");
        if (i > -1) {
            url = url.substring(i + 1);
        }
        i = url.indexOf(":");
        if (i > -1 && i < url.length() - 1) {
            port = Integer.parseInt(url.substring(i + 1));
            url = url.substring(0, i);
        }
        if (url.length() > 0) {
            host = url;
        }
        return new URLInfo(host, port, path, parameters);
    }
}
