package com.example.suyx_annotation_lib.annotation.dependency_inject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注射器
 * @author Created by suyxin on 2018/3/16 10:26.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Component {
    /**
     *  依赖提供者，实例
     * @return
     */
    String[]  modules();
}
