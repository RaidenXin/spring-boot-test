package com.raiden.aop.annotation;

import com.raiden.signer.SignatureStrategy;
import com.raiden.signer.impl.DefaultSignatureStrategy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: JiangJi
 * @Descriotion:
 * @Date:Created in 2023/3/29 23:41
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SignatureRequired {

    Class<? extends SignatureStrategy> signatureStrategy() default DefaultSignatureStrategy.class;
}
