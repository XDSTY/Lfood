package com.xdsty.userservice.mapper;

import com.xdsty.userservice.entity.UserCompany;
import org.springframework.stereotype.Repository;

/**
 * @author 张富华
 * @date 2020/6/16 17:19
 */
@Repository
public interface UserCompanyMapper {

    Integer insert(UserCompany userCompany);

}
