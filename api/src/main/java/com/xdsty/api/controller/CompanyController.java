package com.xdsty.api.controller;

import com.xdsty.api.config.annotation.PackageResult;
import com.xdsty.api.controller.content.CompanyContent;
import com.xdsty.api.controller.param.CityIdParam;
import com.xdsty.userclient.dto.CompanyListDto;
import com.xdsty.userclient.re.CompanyRe;
import com.xdsty.userclient.service.CompanyService;
import javax.validation.Valid;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 张富华
 * @date 2020/6/19 15:57
 */
@PackageResult
@RestController
@RequestMapping("company")
public class CompanyController {

    @DubboReference(version = "1.0")
    private CompanyService companyService;

    @RequestMapping("list")
    public List<CompanyContent> getCompanyListByCity(@RequestBody @Valid CityIdParam param) {
        CompanyListDto dto = new CompanyListDto();
        dto.setCityId(param.getCityId());
        List<CompanyRe> companyRes = companyService.getCompanyList(dto);
        return companyRes.stream().map(this::convert2CompanyContent).collect(Collectors.toList());
    }

    private CompanyContent convert2CompanyContent(CompanyRe re) {
        CompanyContent content = new CompanyContent();
        content.setId(re.getId());
        content.setAddress(re.getAddress());
        content.setDeliveryTime(re.getDeliveryTime());
        content.setFullName(re.getFullName());
        content.setShortName(re.getShortName());
        return content;
    }

}
