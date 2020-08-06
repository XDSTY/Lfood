package com.xdsty.userservice.serializer.convertor;

import basecommon.serializer.PbConvertor;
import com.google.protobuf.GeneratedMessageV3;
import com.xdsty.userclient.message.UserIntegralMessage;
import com.xdsty.userclient.serializer.UserIntegralMessageProto;
import io.seata.serializer.protobuf.generated.BranchCommitRequestProto;

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
                .setCreateTime(new Date().getTime());
    }

    @Override
    public UserIntegralMessage convert2Model(UserIntegralMessageProto.UserIntegralMessage userIntegralMessage) {
        UserIntegralMessage message = new UserIntegralMessage();
        message.setUserId(userIntegralMessage.getUserId());
        message.setOrderId(userIntegralMessage.getOrderId());
        message.setIntegral(userIntegralMessage.getIntegral());
        message.setCreateTime(userIntegralMessage.getCreateTime());
        GeneratedMessageV3 v3 = userIntegralMessage;
        userIntegralMessage.toByteArray()
        return message;
    }
}
