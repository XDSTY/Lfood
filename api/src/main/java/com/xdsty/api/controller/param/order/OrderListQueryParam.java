package com.xdsty.api.controller.param.order;

import basecommon.util.Page;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 张富华
 * @date 2020/9/17 9:42
 */
@Getter
@Setter
public class OrderListQueryParam extends Page {

    /**
     * 订单状态
     */
    private Integer status;

}
