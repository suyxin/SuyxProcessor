package com.example.suyx_api_lib.inject.compile;

import android.app.Activity;

import com.example.suyx_api_lib.inject.IInject;

import java.lang.reflect.Constructor;

/**
 * Created by suyxin on 2018/3/3.
 */

public class InjectorCompile implements IInject {



    @Override
    public void inject(Activity activity) {
        // 1、
        String classFullName = activity.getClass().getName() + "$$Injector";
        //生成一个实例，完成findViewById
        try {
            Class proxy = Class.forName(classFullName);
            Constructor constructor = proxy.getConstructor(activity.getClass());
            constructor.newInstance(activity);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public String typeName() {
        return "编译时注入";
    }

}
