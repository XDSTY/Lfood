package com.xdsty.api.controller.content.order;

import lombok.Getter;
import lombok.Setter;

/**
 * 个人中心订单模块信息
 */
@Getter
@Setter
public class OrderModuleContent {

    /**
     * 显示的订单状态
     */
    private Integer status;

    /**
     * 显示类型枚举
     */
    private Integer moduleOrder;

    /**
     * 提示数量
     */
    private Integer num;

    /**
     * 模块的icon
     */
    private String icon;

    /**
     * 模块名
     */
    private String moduleName;

    /**
     * 模块类型
     */
    private Integer moduleType;

    /**
     * 是否显示数量
     */
    private boolean showNum;

}
