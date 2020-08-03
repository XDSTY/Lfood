package com.xdsty.api.controller.param;

import basecommon.util.Page;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 张富华
 * @date 2020/7/1 15:15
 */
@Getter
@Setter
public class ProductListQueryParam extends Page {

    @NotNull(message = "城市id不能为空")
    private Long cityId;

    private String productName;

}
