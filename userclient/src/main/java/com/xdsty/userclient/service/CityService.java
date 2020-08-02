package com.xdsty.userclient.service;


import com.xdsty.userclient.dto.CityListDto;
import com.xdsty.userclient.re.CityRe;

import java.util.List;

/**
 * @author 张富华
 * @date 2020/6/17 15:11
 */
public interface CityService {

    /**
     * 获取已开通服务城市列表
     * @param dto 入参
     * @return
     */
    List<CityRe> getCityList(CityListDto dto);

}
