package com.xdsty.userservice.serializer.convertor;

import basecommon.serializer.PbConvertor;
import com.xdsty.userclient.message.UserIntegralMessage;
import com.xdsty.userclient.serializer.UserIntegralMessageProto;

/**
 * @author 张富华
 * @date 2020/8/6 17:49
 */
public class UserIntergalMessageConvertor implements PbConvertor<UserIntegralMessage, UserIntegralMessageProto> {

    @Override
    public UserIntegralMessageProto convert2Proto(UserIntegralMessage userIntegralMessage) {
        return null;
    }

    @Override
    public UserIntegralMessage convert2Model(UserIntegralMessageProto userIntegralMessageProto) {
        UserIntegralMessage message = new UserIntegralMessage();
        message.setUserId(userIntegralMessageProto.);
    }
}
