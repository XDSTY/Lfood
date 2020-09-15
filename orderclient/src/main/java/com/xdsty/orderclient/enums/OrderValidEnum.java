package com.xdsty.orderclient.enums;

/**
 * 付款前校验订单
 * code为负数表示订单错误，需要跳转到其他页面
 * code为正数表示需要刷新当前页面
 * @author 张富华
 * @date 2020/9/15 15:07
 */
public enum OrderValidEnum {
    /**
     * 订单支付校验错误信息
     */
    NO_EXIST_ORDER(-1, "订单不存在"),

    PAY_OVERTIME(-2, "订单已超时"),

    ORDER_CANCAL(-3, "订单已取消"),

    ORDER_CHANGED(-4, "订单状态改变"),

    PRICE_MODIFY(1, "订单金额变动，请重新付款");

    private Integer code;

    private String desc;

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    OrderValidEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
