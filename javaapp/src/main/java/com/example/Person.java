package com.example;

/**
 * Created by renguangkai on 2016/7/21.
 */
public class Person {
    public String type = "Person";
    private String name;

    Person(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Person(String name) {
        this.name = name;
    }

    public void speak() {
        System.out.println("i am a " + type + ", my name is " + name);
    }
}
