package com.example.suyxin.suyxprocessor.di;

/**
 * @author Created by suyxin on 2018/3/16 10:40.
 */


public class TestInjectObject implements ITestObject {
    @Override
    public String getText() {
        return "我是被注入的对象";
    }
}
