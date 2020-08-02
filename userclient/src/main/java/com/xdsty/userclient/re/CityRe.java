package com.xdsty.userclient.re;

import java.io.Serializable;

/**
 * @author 张富华
 * @date 2020/6/17 15:12
 */
public class CityRe implements Serializable {

    private Long cityId;

    private String cityName;

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
