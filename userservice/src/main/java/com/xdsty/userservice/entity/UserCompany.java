package com.xdsty.userservice.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 张富华
 * @date 2020/6/16 17:17
 */
@Getter
@Setter
public class UserCompany {

    private Long userId;

    private Long companyId;

    private Integer status;

}
