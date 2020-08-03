package com.xdsty.api.controller.content;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 张富华
 * @date 2020/7/1 15:16
 */
@Getter
@Setter
public class ProductListContent {

    private Long productId;

    private String productName;

    private String thumbnail;

    private String price;
}
