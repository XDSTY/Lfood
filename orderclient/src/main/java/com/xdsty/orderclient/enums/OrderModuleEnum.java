package com.xdsty.orderclient.enums;

/**
 * 个人中心订单模块
 */
public enum OrderModuleEnum {
    /**
     * 枚举
     */
    WAIT_PAY(1, "待付款", 2),

    WAIT_DELIVER(2,"待配送",  4),

    FINISH(3, "已完成", 7),

    REFUND(4,"退款", 8);
    private Integer moduleType;

    private String moduleName;

    private Integer status;

    OrderModuleEnum(Integer moduleType, String moduleName, Integer status) {
        this.moduleType = moduleType;
        this.moduleName = moduleName;
        this.status = status;
    }

    /**
     * 根据type获取对应的枚举
     * @param type
     * @return
     */
    public static OrderModuleEnum getEnumByType(Integer type) {
        for(OrderModuleEnum e : values()) {
            if(e.getModuleType().equals(type)) {
                return e;
            }
        }
        return null;
    }

    public String getModuleName() {
        return moduleName;
    }

    public Integer getStatus() {
        return status;
    }

    public Integer getModuleType() {
        return moduleType;
    }}
