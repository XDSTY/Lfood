package com.xdsty.userclient.service;


import com.xdsty.userclient.dto.CompanyListDto;
import com.xdsty.userclient.re.CompanyRe;

import java.util.List;

/**
 * @author 张富华
 * @date 2020/6/17 14:52
 */
public interface CompanyService {

    /**
     * 根据城市查询公司列表
     * @param dto 入参
     * @return
     */
    List<CompanyRe> getCompanyList(CompanyListDto dto);

}
