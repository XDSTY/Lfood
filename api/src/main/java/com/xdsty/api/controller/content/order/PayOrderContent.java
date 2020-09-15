package com.xdsty.api.controller.content.order;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 张富华
 * @date 2020/9/15 15:32
 */
@Getter
@Setter
public class PayOrderContent {

    /**
     * 支付状态
     */
    private Boolean success;

    /**
     * 提示
     */
    private String msg;

}
