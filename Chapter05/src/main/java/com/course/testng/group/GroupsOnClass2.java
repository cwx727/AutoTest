package com.course.testng.group;

import org.testng.annotations.Test;

@Test(groups = "stu")
public class GroupsOnClass2 {
    public void test1() {
        System.out.println("class2test1");
    }
    public void test2() {
        System.out.println("class2test2");
    }
}
