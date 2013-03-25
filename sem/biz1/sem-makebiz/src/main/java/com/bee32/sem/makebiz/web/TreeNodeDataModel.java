package com.bee32.sem.makebiz.web;

import java.io.Serializable;
import java.math.BigDecimal;

public class TreeNodeDataModel
        implements Serializable {
    private static final long serialVersionUID = 1L;
    String materialLabel;
    BigDecimal total;
    BigDecimal produced;

    TreeNodeDataModel(String materialLabel, BigDecimal total, BigDecimal produced) {
        this.materialLabel = materialLabel;
        this.total = total;
        this.produced = produced;
    }

    public String getMaterial() {
        return materialLabel;
    }

    public void setMaterial(String materialLabel) {
        this.materialLabel = materialLabel;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getProduced() {
        return produced;
    }

    public void setProduced(BigDecimal produced) {
        this.produced = produced;
    }

}