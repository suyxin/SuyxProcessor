package com.example.suyxin.suyxprocessor.di.cdi;

import com.example.suyx_annotation_lib.annotation.dependency_inject.Component;
import com.example.suyxin.suyxprocessor.di.DiTestActivity;

/**
 * @author Created by suyxin on 2018/3/16 10:32.
 */

@Component(modules = {"com.example.suyxin.suyxprocessor.di.cdi.MyModule"})
public interface MyComponent {

    void plus(DiTestActivity activity);

}
