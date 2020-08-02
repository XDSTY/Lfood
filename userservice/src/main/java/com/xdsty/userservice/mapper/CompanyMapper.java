package com.xdsty.userservice.mapper;

import com.xdsty.userservice.entity.Company;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyMapper {

    List<Company> selectCompanyByCityId(Long cityId);

}
