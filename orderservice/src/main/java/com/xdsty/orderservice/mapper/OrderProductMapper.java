package com.xdsty.orderservice.mapper;

import com.xdsty.orderservice.entity.OrderProduct;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderProductMapper {

    /**
     * 添加订单商品
     * @param products 商品列表
     * @return 成功添加的条数
     */
    int addOrderProduct(@Param("products") List<OrderProduct> products);

    /**
     * 获取订单的商品
     * @param orderId 订单id
     * @return
     */
    List<OrderProduct> getOrderProductList(Long orderId);

}
