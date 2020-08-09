package com.xdsty.userservice.mapper;

import com.xdsty.userservice.entity.UserIntegral;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserIntegralMapper {

    /**
     * 批量插入数据
     * @param list
     * @return
     */
    int insertList(List<UserIntegral> list);

    /**
     * 插入单独的数据
     * @param userIntegral
     * @return
     */
    int insertOne(UserIntegral userIntegral);

}
