package com.bee32.sem.inventory.dto;

import java.math.BigDecimal;

import javax.persistence.Transient;

import com.bee32.sem.inventory.entity.StockOrderItem;
import com.bee32.sem.inventory.entity.StocktakingItem;

public class StocktakingOrderItemDto
        extends StockOrderItemDto {

    private static final long serialVersionUID = 1L;

    BigDecimal expectedQuantity;

    @Override
    protected void _marshal(StockOrderItem _source) {
        super._marshal(_source);
        StocktakingItem source = (StocktakingItem) _source;
        expectedQuantity = source.getExpectedQuantity();
    }

    public BigDecimal getExpectedQuantity() {
        return expectedQuantity;
    }

    public void setExpectedQuantity(BigDecimal expectedQuantity) {
        this.expectedQuantity = expectedQuantity;
    }

    @Transient
    public BigDecimal getDiffQuantity() {
        return getQuantity();
    }

    public void setDiffQuantity(BigDecimal diffQuantity) {
        setQuantity(diffQuantity);
    }

    @Transient
    public BigDecimal getActualQuantity() {
        // diff = actual - expected
        // actual = expected + diff
        BigDecimal expected = getExpectedQuantity(); // nullable, but should warn.
        BigDecimal diff = getDiffQuantity(); // non-null
        if (expected == null)
            return null; // warn
        BigDecimal actual = expected.add(diff);
        return actual;
    }

    public void setActualQuantity(BigDecimal actualQuantity) {
        BigDecimal expectedQuantity = getExpectedQuantity(); // nullable, but should warn.
        if (expectedQuantity == null)
            throw new IllegalStateException("Expected quantity is unknown yet.");
        BigDecimal diffQuantity = actualQuantity.subtract(expectedQuantity);
        setDiffQuantity(diffQuantity);
    }

}
