package com.xdsty.userservice.service;

import com.xdsty.userservice.entity.UserIntegral;
import com.xdsty.userservice.entity.enums.UserIntegralEnum;
import com.xdsty.userservice.mapper.UserIntegralMapper;
import com.xdsty.userservice.mapper.UserIntegralRecordMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 张富华
 * @date 2020/8/10 9:56
 */
@Service
public class UserIntegralService {

    private UserIntegralMapper userIntegralMapper;

    private UserIntegralRecordMapper userIntegralRecordMapper;

    public UserIntegralService(UserIntegralMapper userIntegralMapper, UserIntegralRecordMapper userIntegralRecordMapper) {
        this.userIntegralMapper = userIntegralMapper;
        this.userIntegralRecordMapper = userIntegralRecordMapper;
    }

    /**
     * 增加用户的积分，并将原积分记录置为完成
     * @param userIntegral
     */
    @Transactional(rollbackFor = Exception.class)
    public void calculateUserIntegral(UserIntegral userIntegral) {
        UserIntegral integral = userIntegralMapper.getOne(userIntegral.getId());
        if(integral == null || !UserIntegralEnum.INIT.status.equals(integral.getStatus())) {
            return;
        }
        int count = userIntegralMapper.updateRecord(userIntegral.getId(), UserIntegralEnum.SEND.status);
        if(count <= 0) {
            throw new RuntimeException("更新积分记录失败");
        }
        count = userIntegralRecordMapper.incrUserIntegral(userIntegral);
        if(count <= 0) {
            throw new RuntimeException("增加用户积分失败");
        }
    }

}
