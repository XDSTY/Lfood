package com.xdsty.userservice.service;

import basecommon.exception.BusinessRuntimeException;
import com.xdsty.userclient.dto.UserDetailDto;
import com.xdsty.userclient.dto.UserIdDto;
import com.xdsty.userclient.dto.UserLoginDto;
import com.xdsty.userclient.dto.UserRegisterDto;
import com.xdsty.userclient.re.CityRe;
import com.xdsty.userclient.re.UserCompanyInfoRe;
import com.xdsty.userclient.re.UserDetailRe;
import com.xdsty.userclient.re.UserIntegralRe;
import com.xdsty.userclient.re.UserLoginRe;
import com.xdsty.userclient.service.UserService;
import com.xdsty.userservice.entity.*;
import com.xdsty.userservice.mapper.UserCompanyMapper;
import com.xdsty.userservice.mapper.UserIntegralRecordMapper;
import com.xdsty.userservice.mapper.UserMapper;
import com.xdsty.userservice.util.MD5Util;
import com.xdsty.userservice.util.ObjConvert;
import javax.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 张富华
 * @date 2020/6/16 15:31
 */
@Service
@DubboService(timeout = 3000, version = "1.0")
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserCompanyMapper userCompanyMapper;

    @Resource
    private UserIntegralRecordMapper recordMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void registerUser(UserRegisterDto dto) {
        if (userMapper.checkExistByPhone(dto.getLinkPhone()) != null) {
            throw new BusinessRuntimeException("该用户手机号已注册");
        }
        if (userMapper.checkExistByUsername(dto.getUsername()) != null) {
            throw new BusinessRuntimeException("该用户名已存在");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(MD5Util.md5Hex(dto.getPassword()));
        user.setLinkPhone(dto.getLinkPhone());
        user.setCompanyId(dto.getCompanyId());
        user.setProfilePic(dto.getProfilePic());
        // 新建用户
        userMapper.insertUser(user);
        // 新建用户公司关联记录
        UserCompany userCompany = new UserCompany();
        userCompany.setUserId(user.getId());
        userCompany.setCompanyId(dto.getCompanyId());
        userCompanyMapper.insert(userCompany);
    }

    @Override
    public UserLoginRe login(UserLoginDto dto) {
        if (userMapper.checkExistByPhone(dto.getPhone()) == null) {
            throw new BusinessRuntimeException("用户不存在");
        }
        UserLogin userLogin = new UserLogin();
        userLogin.setPhone(dto.getPhone());
        userLogin.setPassword(MD5Util.md5Hex(dto.getPassword()));
        User user = userMapper.selectUserByPhoneAndPsd(userLogin);
        if (user == null) {
            throw new BusinessRuntimeException("密码错误，请重试");
        }
        UserLoginRe re = new UserLoginRe();
        re.setUserId(user.getId());
        re.setLinkPhone(user.getLinkPhone());
        re.setCompanyId(user.getCompanyId());
        re.setUsername(user.getUsername());
        return re;
    }

    @Override
    public UserDetailRe getUserDetail(UserDetailDto dto) {
        UserDetail user = userMapper.getUserDetail(dto.getUserId());
        UserDetailRe re = new UserDetailRe();
        re.setUserId(user.getUserId());
        re.setCompanyId(user.getCompanyId());
        re.setCompanyName(user.getShortName());
        re.setLinkPhone(user.getLinkPhone());
        re.setProfilePic(user.getProfilePic());
        re.setUsername(user.getUsername());
        re.setCityId(user.getCityId());
        re.setCityName(user.getCityName());
        return re;
    }

    @Override
    public CityRe getUserNowCity(Long userId) {
        City city = userMapper.selectUserNowCity(userId);
        return ObjConvert.convertCityRe(city);
    }

    @Override
    public UserCompanyInfoRe getUserCompanyInfo(UserIdDto dto) {
        UserCompanyInfoRe re = new UserCompanyInfoRe();
        UserDetail userDetail = userMapper.getUserDetail(dto.getUserId());
        re.setCompanyAddr(userDetail.getCompanyAddr());
        re.setCompanyId(userDetail.getCompanyId());
        re.setCompanyName(userDetail.getShortName());
        return re;
    }

    @Override
    public UserIntegralRe getUserIntegral(UserIdDto dto) {
        UserIntegralRecord record = recordMapper.getOneByUserId(dto.getUserId());
        UserIntegralRe re = new UserIntegralRe();
        re.setIntegral(record.getIntegral());
        return re;
    }
}
