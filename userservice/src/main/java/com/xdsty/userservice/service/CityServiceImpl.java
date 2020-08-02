package com.xdsty.userservice.service;

import com.xdsty.userclient.dto.CityListDto;
import com.xdsty.userclient.re.CityRe;
import com.xdsty.userclient.service.CityService;
import com.xdsty.userservice.entity.City;
import com.xdsty.userservice.mapper.CityMapper;
import com.xdsty.userservice.util.ObjConvert;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@DubboService(version = "1.0", timeout = 3000)
public class CityServiceImpl implements CityService {

    private CityMapper cityMapper;

    @Autowired
    public void setCityMapper(CityMapper cityMapper) {
        this.cityMapper = cityMapper;
    }

    @Override
    public List<CityRe> getCityList(CityListDto cityListDto) {
        List<City> cities = cityMapper.selectAllCity();
        return cities.stream().map(ObjConvert::convertCityRe).collect(Collectors.toList());
    }
}
