package com.xdsty.userservice.service;

import com.xdsty.userclient.dto.CompanyListDto;
import com.xdsty.userclient.re.CompanyRe;
import com.xdsty.userclient.service.CompanyService;
import com.xdsty.userservice.entity.Company;
import com.xdsty.userservice.mapper.CompanyMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@DubboService(version = "1.0", timeout = 3000)
public class CompanyServiceImpl implements CompanyService {

    private CompanyMapper companyMapper;

    @Autowired
    public void setCompanyMapper(CompanyMapper companyMapper) {
        this.companyMapper = companyMapper;
    }

    @Override
    public List<CompanyRe> getCompanyList(CompanyListDto companyListDto) {
        List<Company> companies = companyMapper.selectCompanyByCityId(companyListDto.getCityId());
        return companies.stream().map(this::convertCompanyRe).collect(Collectors.toList());
    }

    private CompanyRe convertCompanyRe(Company company){
        CompanyRe re = new CompanyRe();
        re.setId(company.getCompanyId());
        re.setFullName(company.getFullName());
        re.setShortName(company.getShortName());
        re.setAddress(company.getAddress());
        re.setDeliveryTime(company.getDeliveryTime());
        return re;
    }
}
