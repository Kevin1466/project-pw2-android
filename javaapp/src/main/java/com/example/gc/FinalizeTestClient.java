package com.example.gc;

/**
 * Created by renguangkai on 2016/7/26.
 */
public class FinalizeTestClient {
    public static void main(String[] args) {
        // TestFinalize tf = new TestFinalize();
        new TestFinalize();
        System.gc();
    }
}
