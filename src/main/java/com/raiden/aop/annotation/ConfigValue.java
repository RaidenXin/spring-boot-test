package com.raiden.aop.annotation;

import java.lang.annotation.*;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 22:48 2020/4/8
 * @Modified By:
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigValue {

    String value();
}
