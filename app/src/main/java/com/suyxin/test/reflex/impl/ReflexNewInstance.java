package com.suyxin.test.reflex.impl;

import com.suyxin.test.reflex.ITest;

/**
 * @author Created by suyxin on 2018/3/9 11:53.
 */


public class ReflexNewInstance implements ITest {
    @Override
    public void test() {
        try {
            TestOjbect o = TestOjbect.class.newInstance();
            o.test();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String name() {
        return "反射生成实例调用";
    }
}
