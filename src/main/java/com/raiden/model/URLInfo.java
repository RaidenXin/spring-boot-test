package com.raiden.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 解析出来的url信息
 */
@Getter
@Setter
public class URLInfo {

    private String host;
    private int port;
    private String path;
    private Map<String, String> parameters;

    public URLInfo(String host, int port, String path, Map<String, String> parameters){
        this.host = host;
        this.port = port;
        this.path = path;
        this.parameters = parameters;
    }
}
