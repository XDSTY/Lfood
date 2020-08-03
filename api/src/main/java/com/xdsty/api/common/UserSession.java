package com.xdsty.api.common;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

/**
 * @author 张富华
 * @date 2020/7/1 17:36
 */
@Getter
@Setter
public class UserSession implements Serializable {

    private Long userId;

    private String username;

    private Long companyId;

    private String companyName;

    private String linkPhone;

    private Long cityId;

    private String cityName;
}
