package com;

import java.sql.Date;

import com.dbwork.autodata.OrderListAuto;
import com.dbwork.autodata.OrderListAutoBuild;

public class Test {

    public static void main(String[] args) {

        OrderListAutoBuild ab = OrderListAutoBuild.builder().min_count_per_order(2).max_count_per_order(10)
                .min_order_per_day(2).max_order_per_day(5).startDate(new Date(2020, 4, 9)).how_many_days(2).build();

        OrderListAuto oa = new OrderListAuto(ab);
    }
}
