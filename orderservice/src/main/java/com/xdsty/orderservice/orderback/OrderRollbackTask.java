package com.xdsty.orderservice.orderback;

import com.xdsty.orderservice.common.Constant;
import com.xdsty.orderservice.util.RedisUtil;
import com.xdsty.orderservice.util.ZSetListUtil;


/**
 * 将订单信息放入redis任务
 */
public class OrderRollbackTask implements Runnable{

    private OrderRollBackInfo orderRollBackInfo;

    public OrderRollbackTask(OrderRollBackInfo orderRollBackInfo) {
        this.orderRollBackInfo = orderRollBackInfo;
    }

    @Override
    public void run() {
        String zsetKey = ZSetListUtil.random();
        // TODO 错误处理
        // k-v形式存储
        RedisUtil.set(Constant.ORDER_BACK_PREFIX + orderRollBackInfo.getOrderId(), orderRollBackInfo, Constant.ORDER_BACK_TTL);
        RedisUtil.zadd(zsetKey, orderRollBackInfo.getOrderId(), orderRollBackInfo.getEndTime());
    }
}
