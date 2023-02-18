package com.raiden.aop;

import com.raiden.aop.annotation.ConfigValue;
import com.raiden.config.CommonConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 22:33 2020/4/8
 * @Modified By:
 */
@Aspect
@Component
public class ConfigAspect extends AbstractDynamicDataSourceAspect{

    private final Map<String, String> cach;
    private static final String IS = "is";
    private static final String GET = "get";
    private static final String Language_Placeholder = "{language}";

    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private Environment env;

    public ConfigAspect(){
        cach = new HashMap<>();
    }

    @PostConstruct
    private void initCach(){
        Class clazz = CommonConfiguration.class;
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields){
            String fieldName = field.getName();
            ConfigValue configValue = field.getAnnotation(ConfigValue.class);
            if (configValue != null){
                String methodName;
                Class<?> type = field.getType();
                if (type == boolean.class){
                    methodName = fieldName.startsWith(IS)? fieldName : IS + firstLetterCapitalized(fieldName);
                }else {
                    methodName = GET + firstLetterCapitalized(fieldName);
                }
                try {
                    Method method = clazz.getMethod(methodName);
                    if (method != null){
                        cach.put(methodName, configValue.value());
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 首字母大写的方法
     * @param name
     * @return
     */
    public static String firstLetterCapitalized(String name){
        char[] chars = name.toCharArray();
        StringBuilder builder = new StringBuilder();
        char c = chars[0];
        //如果是小写才替换
        if (c > 96 && c < 123){
            c -= 32;
            chars[0] = c;
        }
        builder.append(chars);
        return builder.toString();
    }

    @Pointcut("@within(com.raiden.aop.annotation.I18nConfig)")
    public void executionGetConfigValue(){
    }
    @Pointcut("execution(* get*()) || execution(* is*())")
    public void executionGetConfigValue2(){
    }

    @Around("executionGetConfigValue() && executionGetConfigValue2()")
    public Object getConfigValue(ProceedingJoinPoint joinPoint){
        //获取方法名称
        String methodName = joinPoint.getSignature().getName();
        String path = httpServletRequest.getServletPath();
        String[] pathSegment = StringUtils.split(path, "/");
        String language = pathSegment[pathSegment.length - 1];
        String propertyKey = cach.get(methodName);
        String property;
        MethodSignature signature = (MethodSignature ) joinPoint.getSignature();
        Class returnType = signature.getReturnType();
        if (StringUtils.isBlank(propertyKey)){
            if (boolean.class == returnType){
                return false;
            }else if (int.class == returnType){
                return 0;
            }else if (double.class == returnType){
                return 0D;
            }
            return null;
        }else if (propertyKey.indexOf(Language_Placeholder) > -1){
            property = env.getProperty(StringUtils.replace(propertyKey, Language_Placeholder, language));
        }else {
            property = env.getProperty(propertyKey);
        }
        if (boolean.class == returnType){
            return Boolean.parseBoolean(property);
        }else if (int.class == returnType){
            return Integer.parseInt(property);
        }else if (double.class == returnType){
            return Double.parseDouble(property);
        }else if (Double.class == returnType){
            return Double.valueOf(property);
        }else if (Boolean.class == returnType){
            return Boolean.valueOf(property);
        }else if (Integer.class == returnType){
            return Integer.valueOf(property);
        }
        return property;
    }


}
