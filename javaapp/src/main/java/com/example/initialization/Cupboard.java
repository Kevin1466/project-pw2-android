package com.example.initialization;

import static java.lang.System.out;
/**
 * Created by renguangkai on 2016/7/26.
 */
public class Cupboard {
    Bowl bowl3 = new Bowl(3);
    static Bowl bowl4 = new Bowl(4);
    public Cupboard() {
        out.print("Cupboard");
        bowl4.f1(2);
    }
    void f3(int marker) {
        out.print("f3(" + marker + ")");
    }
    static Bowl bowl5 = new Bowl(5);
}
