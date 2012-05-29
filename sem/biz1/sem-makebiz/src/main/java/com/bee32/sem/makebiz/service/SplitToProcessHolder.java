package com.bee32.sem.makebiz.service;

import java.io.Serializable;
import java.math.BigDecimal;

public class SplitToProcessHolder implements Serializable {

    private static final long serialVersionUID = 1L;


    private BigDecimal quantity;
    private String batchNumber;

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }
}
