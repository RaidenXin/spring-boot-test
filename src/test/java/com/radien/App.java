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
        Method[] declaredMethods = App.class.getDeclaredMethods();
        Method method = null;
        for (Method m : declaredMethods){
            if (m.getName().equals("getUser")){
                method = m;
            }
        }
        if (method != null){
            Type genericSuperclass = method.getGenericParameterTypes()[0];
            System.err.println(genericSuperclass instanceof ParameterizedType);
            String str = "{\n" +
                    "\t\"name\":\"张三\",\n" +
                    "\t\"t\":{\n" +
                    "\t\t\"name\":\"master\",\n" +
                    "\t\t\"ids\":[\"1\",\"2\",\"3\"]\n" +
                    "\t}\n" +
                    "}";
            User<Permissions> user = JSON.parseObject(str, genericSuperclass);
            System.err.println(user);
        }
   }

    private static <T> User<T> parseResultV2(String json, Type clazz) {
        return JSON.parseObject(json, new TypeReference<User<T>>(clazz) {
        });
    }
}
