package com.xdsty.payservice.enums;

/**
 * @author 张富华
 */
public enum PayFlowTypeEnum {

    /**
     * 支出
     */
    PAY(1, "支出");

    private Integer status;

    private String desc;

    PayFlowTypeEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }
}
