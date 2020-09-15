package com.xdsty.orderclient.enums;

/**
 * 个人中心订单模块
 */
public enum OrderModuleEnum {
    /**
     * 枚举
     */
    WAIT_PAY("待付款", 2),

    WAIT_DELIVER("待配送", 4),

    FINISH("已完成", 7),

    REFUND("退款", 8);

    private String moduleName;

    private Integer status;

    OrderModuleEnum(String moduleName, Integer status) {
        this.moduleName = moduleName;
        this.status = status;
    }

    public String getModuleName() {
        return moduleName;
    }

    public Integer getStatus() {
        return status;
    }
}
