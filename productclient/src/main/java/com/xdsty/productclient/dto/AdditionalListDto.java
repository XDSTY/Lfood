package com.xdsty.productclient.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author 张富华
 * @date 2020/9/17 17:06
 */
public class AdditionalListDto implements Serializable {

    /**
     * 附加项列表
     */
    private List<Long> itemIds;

    /**
     * 是否只查询有效的附加项
     */
    private boolean valid;

    public List<Long> getItemIds() {
        return itemIds;
    }

    public void setItemIds(List<Long> itemIds) {
        this.itemIds = itemIds;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
