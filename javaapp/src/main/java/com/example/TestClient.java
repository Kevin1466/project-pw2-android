package com.example;

/**
 * Created by renguangkai on 2016/7/21.
 */
public class TestClient {
    public static void main(String[] args) {
        Person p1 = new Person("Tom");
        Person p2 = new Person("Jack");
        magic(p1, p2);
        p1.speak();
        p2.speak();

        Student p3 = new Student("Amy");
        p3.speak();

        Person p4 = new Student("Lily");
        p4.speak();

        Person p5 = p3;
        p5.speak();
    }

    private static void magic(Person p1, Person p2) {
        Person tmp;
        tmp = p1;
        p1 = p2;
        p2 = tmp;
    }
}
