package com.example.suyx_api_lib.inject;

import android.app.Activity;

/**
 * Created by suyxin on 2018/3/3.
 */

public interface IInject {

    void inject(Activity activity);
    String typeName();
}
