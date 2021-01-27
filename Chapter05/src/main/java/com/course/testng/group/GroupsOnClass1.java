package com.course.testng.group;

import org.testng.annotations.Test;

@Test(groups = "stu")
public class GroupsOnClass1 {
    public void test1() {
        System.out.println("class1test1");
    }
    public void test2() {
        System.out.println("class1test2");
    }
}
