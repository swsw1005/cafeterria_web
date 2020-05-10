package com.swsw.util;

public class Rand {

    public static Integer between(int a, int b) {
        int randBetween = 0;
        int a_ = Math.min(a, b);
        int b_ = Math.max(a, b);
        randBetween = (int) (Math.random() * (b_ - a_ + 1) + a_);
        return randBetween;
    }

    public static Double between(double a, double b) {
        double randBetween = 0;
        double a_ = Math.min(a, b);
        double b_ = Math.max(a, b);
        randBetween = Math.random() * (b_ - a_) + a_;
        return randBetween;
    }

}