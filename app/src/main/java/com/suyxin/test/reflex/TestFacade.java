package com.suyxin.test.reflex;

import com.suyxin.test.reflex.impl.NewObjectTest;
import com.suyxin.test.reflex.impl.ReflexInvoke;
import com.suyxin.test.reflex.impl.ReflexNewInstance;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Created by suyxin on 2018/3/9 11:40.
 */


public class TestFacade {

    private static List<ITest> list = new ArrayList<>();

    public static void startTest(){
        buildTest();
        Iterator<ITest> iTestIterator = list.iterator();
        while (iTestIterator!=null&&iTestIterator.hasNext()) {

            ITest test = iTestIterator.next();
            long startTime = System.currentTimeMillis();
            long cout = 100000;
            for (int i = 0; i < cout; i++) {
                test.test();
            }
            long time = System.currentTimeMillis() - startTime;

            System.out.println(String.format("测试：%s,耗时:%s ms",test.name(),time));
        }
    }

    private static void buildTest(){
        list.clear();
        list.add(new NewObjectTest());
        list.add(new ReflexInvoke());
        list.add(new ReflexNewInstance());
    }
}
