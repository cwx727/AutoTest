package com.course.testng.parameter;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class DataProviderTest {
    @Test(dataProvider = "data")
    public void test1(String name, int age) {
        System.out.println("test1 name=" + name + " age=" + age);
    }

    @DataProvider(name = "data")
    public Object[][] providerData() {
        Object[][] o = new Object[][] {
                {"zhangsan", 10},
                {"lisi", 20},
                {"wangwu", 30}
        };
        return o;
    }

    @Test(dataProvider = "methoddata")
    public void test2(String name, int age) {
        System.out.println("test2 name=" + name + " age=" + age);
    }

    @Test(dataProvider = "methoddata")
    public void test3(String name, int age) {
        System.out.println("test3 name=" + name + " age=" + age);
    }

    @DataProvider(name="methoddata")
    public Object[][] providerMethod(Method method) {
        Object[][] result = null;
        if(method.getName().equals("test2")) {
            result = new Object[][] {
                    {"zhangsan", 20},
                    {"zhaosi", 30}
            };
        }else if(method.getName().equals("test3")) {
            result = new Object[][] {
                    {"wangwu", 50}
            };
        }
        return result;
    }
}
