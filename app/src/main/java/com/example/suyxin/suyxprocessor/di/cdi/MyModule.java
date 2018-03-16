package com.example.suyxin.suyxprocessor.di.cdi;

import com.example.suyx_annotation_lib.annotation.dependency_inject.Module;
import com.example.suyx_annotation_lib.annotation.dependency_inject.Provider;
import com.example.suyxin.suyxprocessor.di.ITestObject;
import com.example.suyxin.suyxprocessor.di.TestInjectObject;

/**
 * @author Created by suyxin on 2018/3/16 10:32.
 */

@Module
public class MyModule {

    @Provider
    public ITestObject providerTestObject() {
        return new TestInjectObject();
    }

}
