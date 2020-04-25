package com.raiden.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 11:38 2020/4/25
 * @Modified By:
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface I18nConfig {
}
