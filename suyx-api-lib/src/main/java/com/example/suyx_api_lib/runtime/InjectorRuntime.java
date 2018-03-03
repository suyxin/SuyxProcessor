package com.example.suyx_api_lib.runtime;

import android.app.Activity;

import com.example.suyx_annotation_lib.annotation.ViewInject;
import com.example.suyx_api_lib.IInject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by suyxin on 2018/3/3.
 */

public class InjectorRuntime implements IInject {


    private String METHOD_FIND_VIEW_BY_ID = "findViewById";

    @Override
    public void inject(Activity activity) {


        int count = 0;
        Class<? extends Activity> clazz = activity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            ViewInject inject = field.getAnnotation(ViewInject.class);
            if (inject == null) {
                continue;
            }
            int resId = inject.value();
            if (resId != -1) {
                try {
                    Method method = clazz.getMethod(METHOD_FIND_VIEW_BY_ID,
                            int.class);
                    Object resView = method.invoke(activity, resId);
                    field.setAccessible(true);
                    field.set(activity, resView);
                    count++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }



    }

    @Override
    public String typeName() {

        return "运行时反射时注入";
    }
}
