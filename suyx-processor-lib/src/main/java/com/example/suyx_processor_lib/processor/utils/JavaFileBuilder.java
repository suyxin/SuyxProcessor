package com.example.suyx_processor_lib.processor.utils;

/**
 * @author Created by suyxin on 2018/3/16 13:51.
 */


public class JavaFileBuilder {
    private StringBuilder stringBuilder = new StringBuilder();

    public JavaFileBuilder append(String format, Object... args){
        stringBuilder.append(String.format(format, args));
        return this;
    }

    public StringBuilder getStringBuilder() {
        return stringBuilder;
    }


    public JavaFileBuilder append(JavaFileBuilder fileBuilder){
        stringBuilder.append(fileBuilder.getStringBuilder());
        return this;
    }

    @Override
    public String toString() {
        return stringBuilder.toString();
    }
}
