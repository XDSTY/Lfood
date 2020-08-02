package com.xdsty.userclient.dto;

import java.io.Serializable;

/**
 * @author 张富华
 * @date 2020/6/17 15:01
 */
public class CompanyListDto implements Serializable {

    private Long cityId;

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }
}
