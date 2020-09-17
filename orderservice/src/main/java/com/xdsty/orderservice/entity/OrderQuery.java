package com.xdsty.orderservice.entity;

import basecommon.util.Page;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 张富华
 * @date 2020/9/17 10:37
 */
@Getter
@Setter
public class OrderQuery extends Page {

    private Long userId;

    private Integer status;

}
