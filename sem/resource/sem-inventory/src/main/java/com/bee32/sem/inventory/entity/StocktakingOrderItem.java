package com.bee32.sem.inventory.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * 盘单明细
 *
 * 仓库盘点的明细条目。
 *
 * <p lang="en">
 * Stocktaking Order Item
 */
@Entity
@DiscriminatorValue("STK")
public class StocktakingOrderItem
        extends StockOrderItem {

    private static final long serialVersionUID = 1L;

    BigDecimal expectedQuantity;

    @Override
    public void populate(Object source) {
        if (source instanceof StocktakingOrderItem)
            _populate((StocktakingOrderItem) source);
        else
            super.populate(source);
    }

    protected void _populate(StocktakingOrderItem o) {
        super._populate(o);
        expectedQuantity = o.expectedQuantity;
    }

    /**
     * 期望数量
     *
     * 盘点的期望数量。
     */
    @Column(name = "quantity1", precision = QTY_ITEM_PRECISION, scale = QTY_ITEM_SCALE)
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
