package com.xdsty.userservice.mapper;

import com.xdsty.userservice.entity.City;
import com.xdsty.userservice.entity.User;
import com.xdsty.userservice.entity.UserDetail;
import com.xdsty.userservice.entity.UserLogin;
import org.springframework.stereotype.Repository;

/**
 * @author 张富华
 * @date 2020/6/16 15:41
 */
@Repository
public interface UserMapper {

    Integer insertUser(User user);

    /**
     * 根据手机号判断是否存在
     *
     * @param phone 手机号
     * @return 存在返回1  不存在返回null
     */
    Integer checkExistByPhone(String phone);

    /**
     * 根据用户名判断是否存在
     *
     * @param username 用户名
     * @return 存在返回1  不存在返回null
     */
    Integer checkExistByUsername(String username);

    /**
     * 根据用户手机和密码查找用户
     */
    User selectUserByPhoneAndPsd(UserLogin userLogin);

    /**
     * 根据id获取用户详细信息
     *
     * @param userId 用户id
     * @return 详细信息
     */
    UserDetail getUserDetail(Long userId);

    /**
     * 获取用户当前公司所在城市
     *
     * @param userId 用户id
     * @return
     */
    City selectUserNowCity(Long userId);


}
