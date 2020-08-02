package com.xdsty.userservice.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Company {

    private Long companyId;

    private String fullName;

    private String shortName;

    private Date deliveryTime;

    private String address;

}
