package com.xdsty.productservice.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class Product {

    private Long productId;

    private Long productExtendId;

    private String productName;

    private Date cutOffTime;

    private BigDecimal price;

    private String thumbnail;
}
