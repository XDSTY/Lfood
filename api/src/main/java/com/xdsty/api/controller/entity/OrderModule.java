package com.xdsty.api.controller.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 张富华
 * @date 2020/9/16 14:00
 */
@Getter
@Setter
public class OrderModule {

    /**
     * 显示的订单状态
     */
    private Integer status;

    /**
     * 排序字段
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
