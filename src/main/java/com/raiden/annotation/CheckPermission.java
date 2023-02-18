package com.raiden.annotation;

/**
 * @author: JiangJi
 * @Descriotion:
 * @Date:Created in 2022/12/31 14:06
 */
public @interface CheckPermission {
    String[] value() default {};
}
 
