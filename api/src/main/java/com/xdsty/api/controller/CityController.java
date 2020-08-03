package com.xdsty.api.controller;

import com.xdsty.api.config.annotation.PackageResult;
import com.xdsty.api.controller.content.CityContent;
import com.xdsty.userclient.dto.CityListDto;
import com.xdsty.userclient.re.CityRe;
import com.xdsty.userclient.service.CityService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 张富华
 * @date 2020/6/19 15:51
 */
@PackageResult
@RestController
@RequestMapping("city")
public class CityController {

    @DubboReference(version = "1.0")
    private CityService cityService;

    @RequestMapping("list")
    public List<CityContent> getCityList() {
        CityListDto dto = new CityListDto();
        List<CityRe> cityRes = cityService.getCityList(dto);
        return cityRes.stream().map(this::convert2CityContent).collect(Collectors.toList());
    }

    private CityContent convert2CityContent(CityRe re) {
        CityContent city = new CityContent();
        city.setCityId(re.getCityId());
        city.setCityName(re.getCityName());
        return city;
    }

}
