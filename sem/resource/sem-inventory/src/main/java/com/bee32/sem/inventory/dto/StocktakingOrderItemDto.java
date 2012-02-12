package com.bee32.sem.inventory.dto;

import java.math.BigDecimal;

import javax.persistence.Transient;

import com.bee32.sem.inventory.entity.StockOrderItem;
import com.bee32.sem.inventory.entity.StocktakingOrderItem;

public class StocktakingOrderItemDto
        extends StockOrderItemDto {

    private static final long serialVersionUID = 1L;

    BigDecimal expectedQuantity;

    public StocktakingOrderItemDto() {
        super();
    }

    public StocktakingOrderItemDto(int fmask) {
        super(fmask);
    }

    @Override
    protected boolean isQuantityOptional() {
        return true;
    }

    @Override
    public boolean isDiscardable() {
        return getQuantity() == null;
    }

    @Override
    protected void _marshal(StockOrderItem _source) {
        super._marshal(_source);
        if (_source instanceof StocktakingOrderItem) {
            StocktakingOrderItem source = (StocktakingOrderItem) _source;
            expectedQuantity = source.getExpectedQuantity();
        }
    }

    @Override
    protected void _unmarshalTo(StockOrderItem _target) {
        super._unmarshalTo(_target);
        if (_target instanceof StocktakingOrderItem) {
            StocktakingOrderItem target = (StocktakingOrderItem) _target;
            target.setExpectedQuantity(expectedQuantity);
        }
    }

    public BigDecimal getExpectedQuantity() {
        return expectedQuantity;
    }

    public void setExpectedQuantity(BigDecimal expectedQuantity) {
        this.expectedQuantity = expectedQuantity;
    }

    public BigDecimal getDiffQuantity() {
        return getQuantity();
    }

    public void setDiffQuantity(BigDecimal diffQuantity) {
        setQuantity(diffQuantity);
    }

    // diff = actual - expected
    // actual = expected + diff
    @Transient
    public BigDecimal getActualQuantity() {
        BigDecimal diff = getDiffQuantity(); // nullable
        if (diff == null)
            return null;
        else {
            BigDecimal expected = getExpectedQuantity(); // non-null
            if (expected == null)
                return new BigDecimal(-999);
            BigDecimal actual = expected.add(diff);
            return actual;
        }
    }

    public void setActualQuantity(BigDecimal actualQuantity) {
        BigDecimal diffQuantity;
        if (actualQuantity == null)
            diffQuantity = null;
        else {
            BigDecimal expectedQuantity = getExpectedQuantity(); // nullable, but should warn.
            if (expectedQuantity == null)
                throw new IllegalStateException("Expected quantity is unknown yet.");
            diffQuantity = actualQuantity.subtract(expectedQuantity);
        }
        setDiffQuantity(diffQuantity);
    }

}
