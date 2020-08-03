package com.xdsty.orderservice.mapper;

import com.xdsty.orderservice.entity.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMapper {

    /**
     * 插入状态为待确认状态的订单
     *
     * @param order 订单信息
     * @return
     */
    int insertOrder(Order order);

    /**
     * 修改订单状态
     *
     * @param order 订单
     * @return
     */
    int updateOrder(Order order);

}
