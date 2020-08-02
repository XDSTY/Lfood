package com.xdsty.orderservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 张富华
 * @date 2020/7/9 17:01
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    private Long id;

    private Long productExtendId;

    private Long userId;

    private Integer productNum;
}
