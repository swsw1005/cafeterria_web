package com.util.cal_info;

import java.util.Calendar;

import lombok.Getter;

@Getter
public class Cal_info {
    // 년, 월 입력받아서, void_day, max_day 내보내주는 클래스

    private int void_day; // 앞에 며칠 비냐?
    private int max_day; // 이번달 며칠이냐?

    public Cal_info(int year, int month) {

        calcul(year, month);
    }

    public Cal_info(int yyyymm) {

        String temp = yyyymm + "";
        int year = Integer.parseInt(temp.substring(0, 4));
        int month = Integer.parseInt(temp.substring(4, 6));

        calcul(year, month);
    }

    public Cal_info(String yyyy_mm_dd) {

        yyyy_mm_dd = yyyy_mm_dd.replaceAll("-", "");

        int year = Integer.parseInt(yyyy_mm_dd.substring(0, 4));
        int month = Integer.parseInt(yyyy_mm_dd.substring(4, 6));

        calcul(year, month);
    }

    void calcul(int year, int month) {
        Calendar temp_cal = Calendar.getInstance();
        temp_cal.set(year, month - 1, 1);

        void_day = temp_cal.get(Calendar.DAY_OF_WEEK) - 1;
        System.out.println("void_day " + void_day);

        temp_cal.set(year, month, 0);
        max_day = temp_cal.get(Calendar.DATE);
        System.out.println("max_day " + max_day);

    }
}