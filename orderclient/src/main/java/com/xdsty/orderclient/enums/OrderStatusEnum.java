package com.xdsty.orderclient.enums;

/**
 * @author 张富华
 * @date 2020/9/14 16:38
 */
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
    SUCCESS(4, "付款成功"),
    ORDER_OVERTIME(5, "超时未付款取消"),
    CANCELED(6, "取消"),
    FINISH(7, "完成"),
    REFUND(8,"退款");

    private Integer status;

    private String desc;

    OrderStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
