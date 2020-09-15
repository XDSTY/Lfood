package com.xdsty.orderclient.re;

import java.io.Serializable;

public class OrderModuleRe implements Serializable {

    /**
     * 模块类型
     */
    private Integer moduleType;

    /**
     * 模块订单状态
     */
    private Integer status;

    /**
     * 模块订单数量 已完成和退款不显示
     */
    private Integer num;

    public Integer getModuleType() {
        return moduleType;
    }

    public void setModuleType(Integer moduleType) {
        this.moduleType = moduleType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
