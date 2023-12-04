package com.radien;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class App {
    private void getUser(User<? extends Permissions> user){

    }

    public static void main(String[] args) throws IOException, NoSuchFieldException, NoSuchMethodException {
        System.err.println("A02A01A01A02A01".indexOf("A02A01A01A02A01"));

   }

    private static <T> User<T> parseResultV2(String json, Type clazz) {
        return JSON.parseObject(json, new TypeReference<User<T>>(clazz) {
        });
    }

}
