package com.example.gc;

/**
 * Created by renguangkai on 2016/7/26.
 */
public class Book {
    boolean checkedOut = false;
    Book(boolean checkOut) {
        checkedOut = checkOut;
    }
    void checkIn() {
        checkedOut = false;
    }

    @Override
    protected void finalize() throws Throwable {
        if (checkedOut)
            System.out.println("Error: checked out");
        super.finalize();
    }
}
