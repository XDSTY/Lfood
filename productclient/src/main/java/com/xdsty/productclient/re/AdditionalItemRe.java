package com.xdsty.productclient.re;

import java.io.Serializable;
import java.math.BigDecimal;

public class AdditionalItemRe implements Serializable {

    private static final long serialVersionUID = -8546741473145528550L;

    private Long id;

    private String name;

    private BigDecimal price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
