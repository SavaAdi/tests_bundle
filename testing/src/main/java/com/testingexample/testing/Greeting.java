package com.testingexample.testing;

public class Greeting {

    private static final String HELLO = "HELLO";
    private static final String WORLD = "WORLD";

    public String helloWorld() {
        return HELLO + " " + WORLD;
    }

    public String helloWorld(String name) {
        return HELLO + " " + name;
    }
}
