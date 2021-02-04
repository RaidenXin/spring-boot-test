package com.raiden.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.lang.reflect.Type;

/**
 * Created by ZhouChenmin on 2018/10/8.
 */
//@Slf4j
public class JSONUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        //序列化的规则，包含非null属性，即空串输出
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // 序列化时，忽略空的bean(即沒有任何Field)
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        // 序列化时，忽略在JSON字符串中存在但Java对象实际没有的属性
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // make all member fields serializable without further annotations,
        // instead of just public fields (default setting).
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        // null替换为""
//        objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
//            @Override
//            public void serialize(Object arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException {
//                arg1.writeString("");
//            }
//        });
    }

    public static String writeValueAsString(Object value) {

        try {
            return objectMapper.writeValueAsString(value);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T readValue(String jsonString, Class<T> tClass) {

        try {
            return objectMapper.readValue(jsonString, tClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T readValue(byte[] source, Class<T> tClass) {

        try {
            return objectMapper.readValue(source, tClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T readValue(String jsonString, Type type) {

        try {
            JavaType javaType = objectMapper.getTypeFactory().constructType(type);
            return objectMapper.readValue(jsonString, javaType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T readValue(String jsonString,Class<?> clazz, Type... types) {

        try {
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            JavaType[] javaTypes = new JavaType[types.length];
            for (int i = 0; i < types.length; i++) {
                javaTypes[i] = typeFactory.constructType(types[i]);
            }
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(clazz, javaTypes);
            return objectMapper.readValue(jsonString, javaType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T readValue(String jsonString, TypeReference<T> typeReference) {

        try {
            return objectMapper.readValue(jsonString, typeReference);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
