package com.xdsty.api.controller.content;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

/**
 * @author 张富华
 * @date 2020/6/19 15:59
 */
@Getter
@Setter
public class CompanyContent {

    private Long id;

    private String shortName;

    private String fullName;

    private Date deliveryTime;

    private String address;

}
