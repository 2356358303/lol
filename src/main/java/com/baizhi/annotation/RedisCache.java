package com.baizhi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//声明注解的使用地方
@Target(ElementType.METHOD)
//声明注解的生效时机，运行时生效
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisCache {

}
