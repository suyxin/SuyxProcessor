package com.example.suyx_api_lib;

import android.app.Activity;

import com.example.suyx_api_lib.compile.InjectorCompile;

/**
 * Created by suyxin on 2018/3/3.
 */

public class InjectManager {
    private InjectManager(){
        iInject = new InjectorCompile();
    }
    private volatile static InjectManager instance;
    private IInject iInject;
    public static InjectManager getInstance(){
        if (instance == null) {
            synchronized (InjectManager.class) {
                if (instance == null) {
                    instance = new InjectManager();
                }
            }
        }
        return instance;
    }

    public void inject(Activity activity) {
        if (iInject != null) {
            long start = System.currentTimeMillis();
            iInject.inject(activity);
            long useTime = System.currentTimeMillis() - start;
            System.out.println(String.format("%s %s,耗时%s ms", activity.getClass().getCanonicalName(),iInject.typeName(), useTime));
        }
    }

}
