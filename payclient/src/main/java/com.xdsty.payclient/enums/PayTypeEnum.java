package com.xdsty.payclient.enums;

public enum PayTypeEnum {
    /**
     * 支付类型
     */
    USER_ACCOUNT(1, "用户钱包"),
    PAYPAL(2, "支付宝");

    private Integer type;

    private String desc;

    PayTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
