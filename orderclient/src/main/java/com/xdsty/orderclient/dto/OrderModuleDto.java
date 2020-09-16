package com.xdsty.orderclient.dto;

import java.io.Serializable;
import java.util.List;

public class OrderModuleDto implements Serializable {

    private Long userId;

    private List<Integer> moduleType;

    public List<Integer> getModuleType() {
        return moduleType;
    }

    public void setModuleType(List<Integer> moduleType) {
        this.moduleType = moduleType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
