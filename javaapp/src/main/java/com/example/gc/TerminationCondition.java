package com.example.gc;

/**
 * Created by renguangkai on 2016/7/26.
 */
public class TerminationCondition {
    public static void main(String[] args) {
        Book novel = new Book(true);    // checkedOut
        // Proper cleanup ???
        novel.checkIn();
        // Forget to clean up ???
        new Book(true);
        System.gc();
    }
}
