package com.xdsty.api.controller.param;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 张富华
 * @date 2020/6/16 14:08
 */
@Getter
@Setter
public class UserLoginParam {

    @NotNull(message = "手机号不能为空")
    String phone;

    @NotNull(message = "密码不能忍为空")
    String password;

}