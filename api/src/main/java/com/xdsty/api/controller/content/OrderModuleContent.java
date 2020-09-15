package com.xdsty.api.controller.content;

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
    private Integer orderType;

    /**
     * 提示数量
     */
    private Integer tipNum;

}
