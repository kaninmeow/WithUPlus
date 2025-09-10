package com.withu.constant;

public final class OrderStatusConstant {

    // 待接单
    public static final int PENDING = 0;

    // 进行中
    public static final int IN_PROGRESS = 1;

    // 待评价
    public static final int TO_BE_REVIEWED = 2;

    // 完成
    public static final int COMPLETED = 3;

    // 审核中
    public static final int UNDER_REVIEW = 4;

    //审核不通过
    public static final int REJECTED = 5;

    // 取消订单
    public static final int CANCELED = -1;
}
