package com.xdsty.api.controller.param;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 张富华
 * @date 2020/6/16 17:34
 */
@Getter
@Setter
public class UserRegisterParam {

    @NotNull(message = "用户名不能为空")
    private String username;

    @NotNull(message = "密码不能为空")
    private String password;

    @NotNull(message = "号码不能为空")
    private String phone;

    @NotNull(message = "请选择公司")
    private Long companyId;

    private String profilePic;

}