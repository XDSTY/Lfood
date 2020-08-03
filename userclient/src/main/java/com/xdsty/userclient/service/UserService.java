package com.xdsty.userclient.service;


import com.xdsty.userclient.dto.UserDetailDto;
import com.xdsty.userclient.dto.UserLoginDto;
import com.xdsty.userclient.dto.UserRegisterDto;
import com.xdsty.userclient.re.CityRe;
import com.xdsty.userclient.re.UserDetailRe;
import com.xdsty.userclient.re.UserLoginRe;

/**
 * 用户接口
 */
public interface UserService {

    /**
     * 用户注册
     *
     * @param dto 用户信息
     */
    void registerUser(UserRegisterDto dto);

    /**
     * 用户登录
     *
     * @param dto 用户登录信息
     * @return 登录成功返回用户信息
     */
    UserLoginRe login(UserLoginDto dto);

    /**
     * 获取用户详细信息
     *
     * @param dto id入参
     * @return 用户信息
     */
    UserDetailRe getUserDetail(UserDetailDto dto);

    /**
     * 获取用户所在公司当前城市
     *
     * @param userId 用户id
     * @return
     */
    CityRe getUserNowCity(Long userId);

}
