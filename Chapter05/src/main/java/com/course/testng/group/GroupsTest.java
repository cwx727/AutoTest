package com.course.testng.group;

import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

public class GroupsTest {
    @Test(groups = "server")
    public void test1() {
        System.out.println("serverTest1");
    }

    @Test(groups = "server")
    public void test2() {
        System.out.println("serverTest2");
    }

    @Test(groups = "client")
    public void test3() {
        System.out.println("clientTest3");
    }

    @BeforeGroups("server")
    public void beforeGroupsServer() {
        System.out.println("beforeGroupsServer");
    }

    @AfterGroups("server")
    public void afterGroupsServer() {
        System.out.println("afterGroupsServer");
    }
}
