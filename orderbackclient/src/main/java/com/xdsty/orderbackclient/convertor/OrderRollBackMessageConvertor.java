package com.xdsty.orderbackclient.convertor;

import basecommon.serializer.PbConvertor;
import com.xdsty.orderbackclient.message.OrderRollBackInfo;
import com.xdsty.orderbackclient.serializer.OrderBackMessageProto;

/**
 * @author 张富华
 * @date 2020/9/22 14:57
 */
public class OrderRollBackMessageConvertor implements PbConvertor<OrderRollBackInfo, OrderBackMessageProto.OrderRollBackMessage> {

    private OrderRollBackProductMessageConvertor productMessageConvertor;

    public OrderRollBackMessageConvertor(OrderRollBackProductMessageConvertor productMessageConvertor) {
        this.productMessageConvertor = productMessageConvertor;
    }

    @Override
    public OrderBackMessageProto.OrderRollBackMessage convert2Proto(OrderRollBackInfo orderRollBackInfo) {
        OrderBackMessageProto.OrderRollBackMessage.Builder build = OrderBackMessageProto.OrderRollBackMessage.newBuilder()
                .setOrderId(orderRollBackInfo.getOrderId())
                .setCreateTime(orderRollBackInfo.getCreateTime())
                .setEndTime(orderRollBackInfo.getEndTime())
                .setStatus(orderRollBackInfo.getStatus());
        return build.build();
    }

    @Override
    public OrderRollBackInfo convert2Model(OrderBackMessageProto.OrderRollBackMessage orderRollBackMessage) {
        OrderRollBackInfo orderRollBackInfo = new OrderRollBackInfo();
        orderRollBackInfo.setOrderId(orderRollBackMessage.getOrderId());
        orderRollBackInfo.setCreateTime(orderRollBackMessage.getCreateTime());
        orderRollBackInfo.setEndTime(orderRollBackMessage.getEndTime());
        orderRollBackInfo.setStatus(orderRollBackMessage.getStatus());
        orderRollBackInfo.setProductList(orderRollBackMessage.getProductListList());
        return orderRollBackInfo;
    }
}
