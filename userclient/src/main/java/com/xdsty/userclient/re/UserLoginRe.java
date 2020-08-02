package com.xdsty.userclient.re;

import java.io.Serializable;

public class UserLoginRe implements Serializable {

    private Long userId;

    private String username;

    private Integer sex;

    private Long companyId;

    private String linkPhone;

    private Integer status;

    private String profilePic;

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public Integer getSex() {
        return sex;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public String getLinkPhone() {
        return linkPhone;
    }

    public Integer getStatus() {
        return status;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
}
