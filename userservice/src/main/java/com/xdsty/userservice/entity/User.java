package com.xdsty.userservice.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 张富华
 * @date 2020/6/16 15:35
 */
@Setter
@Getter
public class User implements Serializable {

    private Long id;

    private String username;

    private String password;

    private String profilePic;

    private Integer sex;

    private String linkPhone;

    private String remark;

    private Date createTime;

    private Integer status;

    private Long companyId;

    private Integer integral;

}
