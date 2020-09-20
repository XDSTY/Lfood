package com.xdsty.orderservice.common;

import com.google.common.collect.Lists;
import com.xdsty.orderclient.enums.OrderStatusEnum;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * @author 张富华
 * @date 2020/7/28 16:05
 */
public final class Constant {

    /**
     * 配置中心的groupId
     */
    public static final String CONF_CENTER_GROUPID = "ORDER_SERVICE_GROUP";

    /**
     * 用户积分mq
     */
    public static final String USER_INTEGRAL_TOPIC = "user-integral";

    /**
     * 价格计算默认模式
     */
    public static final RoundingMode PRICE_MODE = RoundingMode.HALF_UP;

    /**
     * 价格2位小数
     */
    public static final int SCALE = 2;

    /**
     * 待支付订单维持时间，默认15分钟
     */
    public static final long DEFAULT_ORDER_WAIT_TIME = 900000;

    /**
     * 订单模块显示数量的状态
     */
    public static final List<Integer> ORDER_MODULE_STATUS = Lists.newArrayList(OrderStatusEnum.WAIT_PAY.getStatus(), OrderStatusEnum.SUCCESS.getStatus());

    /**
     * 公共配置命名空间
     */
    public static String COMMON_CENTER_GROUPID = "COMMON_CONF_GROUP";

    /**
     * 代付款订单数据保存时间 20分钟
     */
    public static final long ORDER_BACK_TTL = 20 * 60 * 1000;

    /**
     * 代付款订单保存到数据库中的前缀  order_back_{orderId}
     */
    public static final String ORDER_BACK_PREFIX = "order_back_";
}
