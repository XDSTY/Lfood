package com.xdsty.orderservice.mapper;

import com.xdsty.orderservice.entity.OrderAdditional;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderAdditionalMapper {

    /**
     * 插入订单商品附加
     * @param orderAdditionals
     * @return
     */
    int insertOrderProductAdditional(@Param("orderAdditionals") List<OrderAdditional> orderAdditionals);

    /**
     * 获取订单商品的附加
     * @param orderId
     * @return
     */
    List<OrderAdditional> getOrderAdditionals(Long orderId);

}
