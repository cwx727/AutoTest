package com.course.testng.suite;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

public class SuiteConfig {
    @BeforeSuite
    public void beforeSuite() {
        System.out.print("beforeSuite");
    }
    @AfterSuite
    public void afterSuite() {
        System.out.print("afterSuite");
    }
    @BeforeTest
    public void beforeTest() {
        System.out.print("beforeTest");
    }
    @AfterTest
    public void afterTest() {
        System.out.print("afterTest");
    }
}
