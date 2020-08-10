package com.xdsty.userservice.mapper;

import com.xdsty.userservice.entity.UserIntegral;
import org.apache.ibatis.annotations.Param;
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
     * 根据id查询记录
     * @param id
     * @return
     */
    UserIntegral getOne(Long id);

}
