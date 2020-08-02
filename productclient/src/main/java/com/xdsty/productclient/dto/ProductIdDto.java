package com.xdsty.productclient.dto;


import java.io.Serializable;

/**
 * @author 张富华
 * @date 2020/6/8 13:48
 */
public class ProductIdDto implements Serializable {

    private static final long serialVersionUID = -8154773187772653948L;

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
