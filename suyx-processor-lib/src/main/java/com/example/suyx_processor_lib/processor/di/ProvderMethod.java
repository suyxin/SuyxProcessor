package com.example.suyx_processor_lib.processor.di;

import java.lang.reflect.Method;

/**
 * @author Created by suyxin on 2018/3/16 11:50.
 */


public class ProvderMethod {
    private Class<?>  host;//归属类
    private Method method;

    public ProvderMethod() {
    }

    public ProvderMethod(Class<?> host, Method method) {
        this.host = host;
        this.method = method;
    }

    public Class<?> getHost() {
        return host;
    }

    public void setHost(Class<?> host) {
        this.host = host;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
