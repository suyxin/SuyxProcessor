package com.suyxin.test.reflex.impl;

import com.suyxin.test.reflex.ITest;

/**
 * @author Created by suyxin on 2018/3/9 11:46.
 */


public class NewObjectTest implements ITest {
    @Override
    public void test() {

        TestOjbect ojbect = new TestOjbect();
        ojbect.test();
    }

    @Override
    public String name() {
        return "直接new实例";
    }
}
