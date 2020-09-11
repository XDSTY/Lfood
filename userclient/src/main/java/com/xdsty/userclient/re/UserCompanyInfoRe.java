package com.xdsty.userclient.re;

import java.io.Serializable;

/**
 * 用户公司信息
 * @author 张富华
 * @date 2020/9/11 17:48
 */
public class UserCompanyInfoRe implements Serializable {

    private Long companyId;

    /**
     * 用户公司名
     */
    private String companyName;

    /**
     * 公司地址
     */
    private String companyAddr;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddr() {
        return companyAddr;
    }

    public void setCompanyAddr(String companyAddr) {
        this.companyAddr = companyAddr;
    }
}
