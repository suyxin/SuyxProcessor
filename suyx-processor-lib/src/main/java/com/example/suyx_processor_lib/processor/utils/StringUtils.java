package com.example.suyx_processor_lib.processor.utils;

/**
 * @author Created by suyxin on 2018/3/16 14:08.
 */


public class StringUtils {
    //首字母大写
    public static String captureName(String name) {
        //     name = name.substring(0, 1).toUpperCase() + name.substring(1);
//        return  name;
        char[] cs=name.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);

    }
}
