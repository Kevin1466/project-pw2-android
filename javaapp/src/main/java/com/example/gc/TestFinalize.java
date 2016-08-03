package com.example.gc;

/**
 * Created by renguangkai on 2016/7/26.
 */
public class TestFinalize {

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalized");
    }
}
