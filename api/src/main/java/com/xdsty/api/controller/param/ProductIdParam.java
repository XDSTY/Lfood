package com.xdsty.api.controller.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 张富华
 * @date 2020/6/8 16:28
 */
@Getter
@Setter
@ApiModel("商品id封装")
public class ProductIdParam {

    @ApiModelProperty(name = "productId", value = "商品id", dataType = "Long", required = true)
    @NotNull(message = "商品id不能为空")
    private Long productId;
}