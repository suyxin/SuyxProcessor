package com.example.suyx_annotation_lib.annotation.dependency_inject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记依赖提供者
 * @author Created by suyxin on 2018/3/16 10:27.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Module {

}
