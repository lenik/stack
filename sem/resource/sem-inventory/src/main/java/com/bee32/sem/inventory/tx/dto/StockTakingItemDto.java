package com.bee32.sem.inventory.tx.dto;

import java.beans.Transient;
import java.math.BigDecimal;

import com.bee32.sem.inventory.dto.StockOrderItemDto;

public class StockTakingItemDto
        extends StockOrderItemDto {

    private static final long serialVersionUID = 1L;

    StockOrderItemDto expectedItem;
    StockOrderItemDto diffItem;

    public StockTakingItemDto() {
        super();
    }

    public StockTakingItemDto(int fmask) {
        super(fmask);
    }

    // public StockTakingItem populate(StockOrderItemDto expectedItem, StockOrderItemDto diffItem) {
    public StockTakingItemDto(StockOrderItemDto expectedItem, StockOrderItemDto diffItem) {
        this();

        if (expectedItem == null)
            throw new NullPointerException("expectedItem");
        if (diffItem == null)
            throw new NullPointerException("diffItem");

        super.populate(expectedItem);

        this.expectedItem = expectedItem;
        this.diffItem = diffItem;
    }

    public BigDecimal getExpected() {
        return expectedItem.getQuantity();
    }

    public void setExpected(BigDecimal expected) {
        if (expected == null)
            throw new NullPointerException("expected");
        expectedItem.setQuantity(expected);
    }

    public BigDecimal getDiff() {
        return diffItem.getQuantity();
    }

    public void setDiff(BigDecimal diff) {
        if (diff == null)
            throw new NullPointerException("diff");
        diffItem.setQuantity(diff);
    }

    @Transient
    public BigDecimal getActual() {
        BigDecimal expected = getExpected();
        BigDecimal diff = getDiff();
        BigDecimal actual = expected.add(diff);
        return actual;
    }

    @Transient
    public void setActual(BigDecimal actual) {
        BigDecimal expected = getExpected();
        BigDecimal diff = actual.subtract(expected);
        setDiff(diff);
    }

    @Override
    public BigDecimal getQuantity() {
        return getActual();
    }

    @Override
    public void setQuantity(BigDecimal quantity) {
        setActual(quantity);
    }

}
