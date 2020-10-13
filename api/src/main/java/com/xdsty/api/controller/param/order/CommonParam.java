package com.xdsty.api.controller.param.order;

import com.xdsty.api.util.SessionUtil;
import java.io.Serializable;

/**
 * @author 张富华
 * @date 2020/10/13 16:06
 */
public class CommonParam implements Serializable {

    private Long userId;

    public Long getUserId() {
        return SessionUtil.getUserId();
    }
}
