package com.xdsty.orderclient.enums;

/**
 * @author 张富华
 * @date 2020/10/13 17:21
 */
public enum OrderAddEnum {
    /**
     * 下单返回状态
     */
    SUCCESS(1, "下单成功"),
    FAIL(-1, "下单失败"),
    TRANSACTION_REPEAT(2, "重复事务"),
    OPERATION_REPEAT(3, "重复操作");

    OrderAddEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    private Integer status;

    private String desc;

    public Integer getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
