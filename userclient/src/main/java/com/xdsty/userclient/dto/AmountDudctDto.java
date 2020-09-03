package com.xdsty.userclient.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户扣款如入参
 * @author 张富华
 * @date 2020/9/3 18:08
 */
public class AmountDudctDto implements Serializable {

    private Long userId;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 扣款类型
     */
    private Integer type;

    /**
     * 订单
     */
    private Long orderId;

}
