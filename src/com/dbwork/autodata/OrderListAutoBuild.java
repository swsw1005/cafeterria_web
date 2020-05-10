package com.dbwork.autodata;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderListAutoBuild {

    private Date startDate;
    private int how_many_days;

    @Builder.Default
    private int min_order_per_day = 0;
    @Builder.Default
    private int max_order_per_day = 2;
    // 기본값 0~2명 입력

    @Builder.Default
    private int min_count_per_order = 1;
    @Builder.Default
    private int max_count_per_order = 5;
    // 기본값 1~5 개 주문

}