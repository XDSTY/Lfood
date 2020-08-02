package com.xdsty.userservice.util;


import com.xdsty.userclient.re.CityRe;
import com.xdsty.userservice.entity.City;

/**
 * @author 张富华
 * @date 2020/7/1 17:14
 */
public final class ObjConvert {

    public static CityRe convertCityRe(City city){
        CityRe re = new CityRe();
        re.setCityId(city.getCityId());
        re.setCityName(city.getCityName());
        return re;
    }
}
