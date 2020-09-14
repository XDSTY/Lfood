package com.xdsty.api.controller.content.order;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 张富华
 * @date 2020/9/14 15:31
 */
@Getter
@Setter
public class OrderPayPageContent {

    /**
     * 订单应付金额
     */
    private BigDecimal shouldPayPrice;

    /**
     * 订单创建时间
     */
    private Date createTime;

    /**
     * 当前时间
     */
    private Date currentTime;

    /**
     * 订单支付截止时间
     */
    private Date endTime;

    /**
     * 跳转到那个页面  1:支付页  2:首页
     */
    private Integer jumpTo;

}
