package com.xdsty.orderclient.service;

import com.xdsty.orderclient.dto.OrderIdDto;
import com.xdsty.orderclient.dto.OrderModuleDto;
import com.xdsty.orderclient.dto.OrderValidDto;
import com.xdsty.orderclient.re.OrderModuleRe;
import com.xdsty.orderclient.re.OrderPayPageRe;
import com.xdsty.orderclient.re.OrderValidRe;

import java.util.List;

/**
 * @author 张富华
 * @date 2020/7/30 9:23
 */
public interface OrderService {

    /**
     * 获取代付款订单的付款信息
     * @param dto
     * @return
     */
    OrderPayPageRe getOrderPayInfo(OrderIdDto dto);

    /**
     * 校验订单状态
     * @param dto
     */
    OrderValidRe checkOrderValid(OrderValidDto dto);

    /**
     * 获取订单模块
     * @param orderModuleDto
     * @return
     */
    List<OrderModuleRe> getOrderModules(OrderModuleDto orderModuleDto);

}
