package com.xdsty.orderservice.mapper;

import com.xdsty.orderservice.entity.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    /**
     * 获取订单
     * @param orderId
     * @return
     */
    Order getOrder(@Param("userId") Long userId, @Param("orderId") Long orderId);

    /**
     * 根据用户和订单状态获取订单列表
     * @param userId 用户id
     * @param statusList 状态列表
     * @return
     */
    List<Order> getOrderListByUserAndStatus(@Param("userId") Long userId, @Param("statusList")List<Integer> statusList);
}
