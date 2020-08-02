package com.xdsty.productservice.entity;

import basecommon.util.Page;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 张富华
 * @date 2020/7/1 11:04
 */
@Getter
@Setter
public class ProductListQuery extends Page {

    private Long cityId;

    private String productName;

}
