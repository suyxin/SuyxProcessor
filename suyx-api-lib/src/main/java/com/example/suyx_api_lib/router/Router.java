package com.example.suyx_api_lib.router;

import android.content.Context;
import android.content.Intent;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author Created by suyxin on 2018/3/7 14:25.
 */


public class Router {

    private static final String PACKAGE = "com.example.suyxin.router";
    private static final String CLASS_SIMPLE_NAME = "Router$$Group";
    private static final String BUILDE_MAP = "buildMap";
    private static Router instance;
    private Map<String,String> map;

    private Router() {
        String fullName = PACKAGE + "." + CLASS_SIMPLE_NAME;

        try {
            Object object = Class.forName(fullName).newInstance();
            Method method = object.getClass().getDeclaredMethod(BUILDE_MAP);
            method.setAccessible(true);
            map = (Map<String, String>) method.invoke(object);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Router getInstance() {
        if (instance == null) {
            synchronized (Router.class) {
                if (instance == null) {
                    instance = new Router();
                }
            }
        }
        return instance;
    }

    public void toActivity(Context context,String path) {
        try {
            Intent intent = new Intent(context, Class.forName(map.get(path)));
            context.startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
