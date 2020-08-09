package com.xdsty.orderservice.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserIntegral {

    private Long id;

    private Integer type;

    private Long userId;

    private Integer integral;

    private Long orderId;
}
