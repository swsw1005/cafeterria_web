package com;

import java.util.*;

public class Test {

    public static void main(String[] args) {

        Calendar cal = Calendar.getInstance();

        int y_ = cal.get(Calendar.YEAR);
        int m_ = cal.get(Calendar.MONTH) + 1;

        System.out.println(y_ + " " + m_);
    }

}
