package com.xdsty.api.controller.content.user;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 张富华
 * @date 2020/9/11 17:45
 */
@Getter
@Setter
public class UserCompanyAddressContent {

    private Long companyId;

    /**
     * 用户公司名
     */
    private String companyName;

    /**
     * 公司地址
     */
    private String companyAddr;

    /**
     * 用户电话，中间段显示**
     */
    private String phone;

    /**
     * 用户名
     */
    private String username;

}
