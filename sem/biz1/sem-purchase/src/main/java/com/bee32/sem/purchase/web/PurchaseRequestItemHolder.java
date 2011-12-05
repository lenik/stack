package com.bee32.sem.purchase.web;

import java.io.Serializable;

import com.bee32.sem.purchase.dto.PurchaseRequestItemDto;

public class PurchaseRequestItemHolder implements Serializable {

    private static final long serialVersionUID = 1L;

    PurchaseRequestItemDto item;
    Long warehouseId;

    public PurchaseRequestItemDto getItem() {
        return item;
    }

    public void setItem(PurchaseRequestItemDto item) {
        this.item = item;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }
}
