package com.xdsty.userservice.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserDetail implements Serializable {

    private Long userId;

    private String username;

    private String profilePic;

    private Long companyId;

    private String shortName;

    private String linkPhone;

    private Long cityId;

    private String cityName;

    private String companyName;

    private String companyAddr;
}
