package com.example.suyx_processor_lib.processor.di;

/**
 * Created by suyxin on 2018/3/16.
 */

public interface IInjectMember<T,R> {
    void inject(T instance,IProvider<R> provider);
}
