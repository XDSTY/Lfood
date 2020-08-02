package com.xdsty.orderservice.entity.enums;

public enum OrderStatus {

    /**
     * 订单状态
     */
    IN_VALID(0, "失效"),
    /**
     * 待确认状态
     */
    WAIT_CONFIRM(2, "待确认"),
    WAIT_PAY(4, "未付款"),
    PAY_FAIL(5, "付款失败"),
    SUCCESS(6, "付款成功");

    public Integer status;

    public String desc;

    OrderStatus(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }
}
