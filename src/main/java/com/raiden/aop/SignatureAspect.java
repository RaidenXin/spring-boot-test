package com.raiden.aop;

import com.raiden.aop.annotation.SignatureRequired;
import com.raiden.exception.SignatureException;
import com.raiden.signer.SignatureStrategy;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Aspect
@Slf4j
public class SignatureAspect {



    private Map<Class<? extends SignatureStrategy>, SignatureStrategy> signatureStrategyMapping;

    @Autowired
    public void setSignatureStrategy(List<SignatureStrategy> signatureStrategies) {
        if (CollectionUtils.isEmpty(signatureStrategies)) {
            signatureStrategyMapping = new HashMap<>();
        }
        signatureStrategyMapping = signatureStrategies.stream().collect(Collectors.toMap(SignatureStrategy::getClass, (SignatureStrategy item) -> item, (a, b) -> a));
    }


    // 签名验证方法将在此处实现
    @Around("@annotation(com.raiden.aop.annotation.SignatureRequired)")
    public Object verifySignature(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取当前切点所在的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        // 获取方法上的注解
        SignatureRequired annotation = AnnotationUtils.getAnnotation(method, SignatureRequired.class);
        // 如果方法上没有注解，则尝试获取类上的注解
        if (Objects.isNull(annotation)) {
            Class<?> targetClass = joinPoint.getTarget().getClass();
            annotation = AnnotationUtils.getAnnotation(targetClass, SignatureRequired.class);
        }
        if (Objects.isNull(annotation)) {
            return joinPoint.proceed();
        }
        final SignatureStrategy signatureStrategy = signatureStrategyMapping.get(annotation.signatureStrategy());
        if (Objects.isNull(signatureStrategy)) {
            log.error("签名策略不存在请联系管理员! className:{}", annotation.signatureStrategy().getName());
            throw new SignatureException("签名策略不存在请联系管理员！");
        }
        // 从方法参数和请求中提取签名，并验证签名的正确性
        // 如果签名验证失败，则抛出异常或采取其他适当的措施
        signatureStrategy.verifySignature(joinPoint);
        // 如果签名验证成功，则继续执行原始方法
        return joinPoint.proceed();
    }

}
