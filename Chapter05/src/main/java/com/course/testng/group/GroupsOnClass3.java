package com.course.testng.group;

import org.testng.annotations.Test;

@Test(groups = "Tea")
public class GroupsOnClass3 {
    public void test1() {
        System.out.println("class3test1");
    }
    public void test2() {
        System.out.println("class3test2");
    }
}
