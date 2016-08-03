package com.example.initialization;
import static java.lang.System.out;

/**
 * Created by renguangkai on 2016/7/26.
 */
public class StaticInitialization {

    public static void main(String[] args) {
        out.println("Creating new Cupboard() in main");
        new Cupboard();
        out.println("Creating new Cupboard() in main");
        new Cupboard();
        table.f2(1);
        cupboard.f3(1);
    }

    static Table table = new Table();
    static Cupboard cupboard = new Cupboard();
}
