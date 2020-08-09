package com.xdsty.orderservice.entity.enums;

public enum OrderStatusEnum {

    /**
     * 订单状态
     */
    IN_VALID(0, "失效"),
    /**
     * 待确认状态
     */
    WAIT_CONFIRM(1, "待确认"),
    WAIT_PAY(2, "未付款"),
    PAY_FAIL(3, "付款失败"),
    SUCCESS(4, "付款成功");

    public Integer status;

    public String desc;

    OrderStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }
}
