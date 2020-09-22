package com.xdsty.orderbackclient.convertor;

import basecommon.serializer.PbConvertor;
import com.xdsty.orderbackclient.message.OrderRollbackProduct;
import com.xdsty.orderbackclient.serializer.OrderRollBackProductMessageProto;

/**
 * @author 张富华
 * @date 2020/9/22 15:26
 */
public class OrderRollBackProductMessageConvertor implements PbConvertor<OrderRollbackProduct, OrderRollBackProductMessageProto.OrderRollBackProductMessage> {

    @Override
    public OrderRollBackProductMessageProto.OrderRollBackProductMessage convert2Proto(OrderRollbackProduct orderRollbackProduct) {
        return OrderRollBackProductMessageProto.OrderRollBackProductMessage.newBuilder()
                .setProductId(orderRollbackProduct.getProductId())
                .setProductNum(orderRollbackProduct.getProductNum())
                .build();
    }

    @Override
    public OrderRollbackProduct convert2Model(OrderRollBackProductMessageProto.OrderRollBackProductMessage orderRollBackProductMessage) {
        OrderRollbackProduct product = new OrderRollbackProduct();
        product.setProductId(orderRollBackProductMessage.getProductId());
        product.setProductNum(orderRollBackProductMessage.getProductNum());
        return product;
    }
}
