package com.example;

/**
 * Created by renguangkai on 2016/7/26.
 */
public class ExpressionTest {
    static String s0;
    public static void main(String[] args) {
        String s1 = "ABC";
        String s2 = "BCD";
        System.out.println("s1 == s2 ? " + (s1 == s2));

        for (char ch : "Hello World...".toCharArray()) {
            System.out.print(ch + " ");
        }

        System.out.println("s0 == null ? " + (s0 == null));

    }
}
