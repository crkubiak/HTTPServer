package com.server;

import org.junit.*;

import static org.junit.Assert.*;

public class HTTPServerTest {
    public HTTPServerTest() {
        System.out.printf("Constructor invoked. Instance: %s%n", this);
    }

    @BeforeClass
    public static void beforeClassMethod() {
        System.out.println("@BeforeClass static method invoked.");
    }

    @Test
    public void test1() {
        System.out.printf("@Test method 1  invoked. Instance: %s%n", this);
    }

    @Test
    public void test2() {
        System.out.printf("@Test method 2  invoked. Instance: %s%n", this);
    }

    @Before
    public void beforeMethod() {
        System.out.printf("@Before method invoked. Instance: %s%n", this);
    }

    @After
    public void afterMethod() {
        System.out.printf("@After method invoked. Instance: %s%n", this);
    }

    @AfterClass
    public static void afterClassMethod() {
        System.out.printf("@AfterClass static method invoked.%n");
    }
}