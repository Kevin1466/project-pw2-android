package com.example;

/**
 * Created by renguangkai on 2016/7/24.
 */
public enum  MyEnum {

    ITEM1("A", "AA"), ITEM2("B", "BB");

    String name1, name2;
    MyEnum(String name1, String name2) {
        this.name1 = name1;
        this.name2 = name2;
    }

    public String getName1() {
        return name1;
    }

    public String getName2() {
        return name2;
    }
}
