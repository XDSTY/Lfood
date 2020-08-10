package com.xdsty.userservice.mapper;

import com.xdsty.userservice.entity.UserIntegral;
import com.xdsty.userservice.entity.UserIntegralRecord;
import org.springframework.stereotype.Repository;

/**
 * @author 张富华
 * @date 2020/8/10 14:22
 */
@Repository
public interface UserIntegralRecordMapper {

    /**
     * 插入一条新记录
     * @param userIntegralRecord
     * @return
     */
    int insertOne(UserIntegralRecord userIntegralRecord);

    /**
     * 增加用户的积分
     * @param userIntegral
     * @return
     */
    int incrUserIntegral(UserIntegral userIntegral);

    /**
     * 查询用户的积分记录
     * @return
     */
    UserIntegralRecord getOneByUserId(Long userId);

}
