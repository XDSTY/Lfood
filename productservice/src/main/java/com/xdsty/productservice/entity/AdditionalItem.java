package com.xdsty.productservice.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AdditionalItem {

    private Long id;

    private String name;

    private BigDecimal price;
}
