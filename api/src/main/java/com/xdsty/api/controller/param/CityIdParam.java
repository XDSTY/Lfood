package com.xdsty.api.controller.param;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 张富华
 * @date 2020/6/19 16:00
 */
@Getter
@Setter
public class CityIdParam {

    @NotNull(message = "城市id不能为空")
    private Long cityId;

}
