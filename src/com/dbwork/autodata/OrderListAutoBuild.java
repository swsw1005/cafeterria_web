package com.dbwork.autodata;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderListAutoBuild {

    private int year;
    private int month;
    private int day;
    private int how_many_days;

    private int min_order_per_day = 0;
    private int max_order_per_day = 2;
    // 기본값 0~2명 입력
    private int min_count_per_order = 1;
    private int max_count_per_order = 5;
    // 기본값 1~5 개 주문

    public void setOrderPerDay(int[] minmax) {
        min_order_per_day = minmax[0];
        max_order_per_day = minmax[1];
    }

    public void setCountPerOrder(int[] minmax) {
        min_count_per_order = minmax[0];
        max_count_per_order = minmax[1];
    }

}