package com.example.extension;

/**
 * Created by renguangkai on 2016/7/27.
 */
public class Detergent extends Cleanser {
    @Override
    public void scrub() {
        append(" Detergent.scrub()");
        super.scrub();
    }
    public void foam() {
        append(" foam()");
    }
    public static void main(String[] args) {
        Detergent x = new Detergent();
        x.dilute();
        x.apply();
        x.scrub();
        x.foam();
        System.out.println(x);  // Cleanser dilute() apply()  Detergent.scrub() scrub() foam()
        System.out.println("Testing base class: ");
        Cleanser.main(args);    // Cleanser dilute() apply() scrub()
    }
}
