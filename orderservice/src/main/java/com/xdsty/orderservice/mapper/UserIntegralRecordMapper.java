package com.xdsty.orderservice.mapper;

import com.xdsty.orderservice.entity.UserIntegral;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 张富华
 * @date 2020/8/7 17:51
 */
@Repository
public interface UserIntegralRecordMapper {

    /**
     * 获取最新的处于未发送状态的记录
     * @return
     */
    List<UserIntegral> getLastRecord(Long lastId);

    /**
     * 更新记录的状态
     * @param id 记录id
     * @param status 状态
     * @return
     */
    int updateRecord(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 插入积分记录
     * @param userIntegral
     * @return
     */
    int insertOne(UserIntegral userIntegral);

}
