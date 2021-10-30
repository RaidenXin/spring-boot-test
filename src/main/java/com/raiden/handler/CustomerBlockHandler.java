package com.raiden.handler;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomerBlockHandler {

    public static String blocHandler() throws IllegalAccessException {
        if (true){
            throw new IllegalAccessException("这里报错了！");
        }
        return "Current limiting";
    }
}
