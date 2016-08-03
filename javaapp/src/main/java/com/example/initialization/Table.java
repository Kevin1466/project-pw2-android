package com.example.initialization;

import static java.lang.System.out;

/**
 * Created by renguangkai on 2016/7/26.
 */
class Table {
    static Bowl bowl = new Bowl(1);
    Table() {
        out.print("Table()");
        bowl2.f1(1);
    }
    void f2(int marker) {
        out.print("f2(" + marker + ")");
    }
    static Bowl bowl2 = new Bowl(2);
}
