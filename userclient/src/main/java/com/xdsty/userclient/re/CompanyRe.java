package com.xdsty.userclient.re;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 张富华
 * @date 2020/6/17 15:02
 */
public class CompanyRe implements Serializable {

    private Long id;

    private String shortName;

    private String fullName;

    private Date deliveryTime;

    private String address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
