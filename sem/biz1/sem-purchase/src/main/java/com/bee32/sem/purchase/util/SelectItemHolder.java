package com.bee32.sem.purchase.util;

import java.math.BigDecimal;

import com.bee32.sem.inventory.dto.StockOrderItemDto;

/**
 * 物料计划中锁定物料时用来接收用户输入的辅助类
 *
 */
public class SelectItemHolder {
    StockOrderItemDto item;
    BigDecimal quantity;
    boolean checked;

    public StockOrderItemDto getItem() {
        return item;
    }

    public void setItem(StockOrderItemDto item) {
        this.item = item;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
