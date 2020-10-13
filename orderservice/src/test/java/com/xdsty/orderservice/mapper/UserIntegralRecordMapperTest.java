package com.xdsty.orderservice.mapper;

import com.xdsty.orderservice.BaseTest;
import com.xdsty.orderservice.entity.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import java.math.BigDecimal;
import java.util.Date;


class UserIntegralRecordMapperTest extends BaseTest {

    @Autowired
    private UserIntegralRecordMapper mapper;

    @Autowired
    private OrderMapper orderMapper;

    @Test
    void updateRecord() {

        mapper.updateRecord(1L, 1);

    }

    @Test
    void testOrder() {
        Order order = new Order();
        order.setUserId(1L);
        order.setPayEndTime(new Date());
        order.setOrderId(1111L);
        order.setStatus(1);
        order.setTotalPrice(BigDecimal.ONE);
        order.setUniqueRow(111L);
        try {
            orderMapper.insertOrder(order);
        }catch (DuplicateKeyException e) {
            System.out.println("asasasasssssssss");
        }
    }
}