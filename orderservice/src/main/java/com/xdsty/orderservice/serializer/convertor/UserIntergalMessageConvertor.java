package com.xdsty.orderservice.serializer.convertor;

import basecommon.serializer.PbConvertor;
import com.xdsty.userclient.message.UserIntegralMessage;
import com.xdsty.userclient.serializer.UserIntegralMessageProto;
import java.util.Date;

/**
 * @author 张富华
 * @date 2020/8/6 17:49
 */
public class UserIntergalMessageConvertor implements PbConvertor<UserIntegralMessage, UserIntegralMessageProto.UserIntegralMessage> {

    @Override
    public UserIntegralMessageProto.UserIntegralMessage convert2Proto(UserIntegralMessage message) {
        return UserIntegralMessageProto.UserIntegralMessage.newBuilder().setUserId(message.getUserId())
                .setOrderId(message.getOrderId())
                .setIntegral(message.getIntegral())
                .setCreateTime(System.currentTimeMillis()).build();
    }

    @Override
    public UserIntegralMessage convert2Model(UserIntegralMessageProto.UserIntegralMessage userIntegralMessage) {
        UserIntegralMessage message = new UserIntegralMessage();
        message.setUserId(userIntegralMessage.getUserId());
        message.setOrderId(userIntegralMessage.getOrderId());
        message.setIntegral(userIntegralMessage.getIntegral());
        message.setCreateTime(new Date(userIntegralMessage.getCreateTime()));
        return message;
    }
}
