package com.raiden.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 23:51 2020/8/27
 * @Modified By:
 */
@Aspect
@Component
public class CurrentLimitingAspect {

    @Pointcut("@annotation(com.raiden.aop.annotation.CurrentLimiting)")
    public void intercept(){}

    @Around("intercept()")
    public Object currentLimitingHandle(ProceedingJoinPoint joinPoint) throws Throwable{
        MethodSignature signature = (MethodSignature ) joinPoint.getSignature();
        String[] parameterNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        Map<String, Object> params = new HashMap<>(parameterNames.length << 1);
        for (int i = 0; i < parameterNames.length; i++) {
            params.put(parameterNames[i], args[i]);
        }
        System.out.println(params);
        return joinPoint.proceed();
    }
}
