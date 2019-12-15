package com.testingexample.testing;

import org.junit.jupiter.api.*;

class GreetingTest {

    Greeting greeting;

    @BeforeAll
    static void beforeAll() {
        System.out.println("BEFORE ALL ... EXECUTED ONCE");
    }

    @BeforeEach
    void setUp() {
        System.out.println("....BEFORE EACH....");
        greeting = new Greeting();
    }

    @Test
    void helloWorld() {
        System.out.println(greeting.helloWorld());
    }

    @Test
    void testHelloWorld() {
        System.out.println(greeting.helloWorld("Friend"));
    }

    @AfterEach
    void tearDown() {
        System.out.println("....AFTER EACH....");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("AFTER ALL ... EXECUTED ONCE...THE END");
    }
}