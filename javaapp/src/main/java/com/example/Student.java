package com.example;

public class Student extends Person {
    private String type = "Student";
    private String name = "default name";

    public Student(String name) {
        //super(name);
        this.name = name;
    }

    @Override
    public void speak() {
        super.speak();
        System.out.println("student speaks");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        // // TODO: 2016/7/26 what to do?
    }
}
