package com.course.testng;

import org.testng.annotations.Test;

public class ExpectedException {
    @Test(expectedExceptions = RuntimeException.class)
    public void test1() {
        System.out.println("expectedExceptions");
        throw new RuntimeException();
    }
}
