package com.suyxin.test.reflex.impl;

import com.suyxin.test.reflex.ITest;

import java.lang.reflect.Method;

/**
 * @author Created by suyxin on 2018/3/9 11:49.
 */


public class ReflexInvoke implements ITest {

    @Override
    public void test() {

        try {
            TestOjbect ojbect = (TestOjbect) Class.forName("com.suyxin.test.reflex.impl.TestOjbect").newInstance();
            Method method = ojbect.getClass().getDeclaredMethod("test");
            method.setAccessible(true);
            method.invoke(ojbect);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public String name() {
        return "反射调用";
    }
}
