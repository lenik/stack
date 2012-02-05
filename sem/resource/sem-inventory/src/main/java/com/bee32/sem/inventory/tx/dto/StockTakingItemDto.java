package com.bee32.sem.inventory.tx.dto;

import java.beans.Transient;
import java.math.BigDecimal;

import com.bee32.sem.inventory.dto.StockOrderItemDto;

public class StockTakingItemDto
        extends StockOrderItemDto {

    private static final long serialVersionUID = 1L;

    BigDecimal expected;

    public StockTakingItemDto() {
        super();
    }

    public StockTakingItemDto(int fmask) {
        super(fmask);
    }

    // public StockTakingItem populate(StockOrderItemDto expectedItem, StockOrderItemDto diffItem) {
    public StockTakingItemDto(StockOrderItemDto expectedItem, StockOrderItemDto diffItem) {
        this();

        super.populate(expectedItem);

        BigDecimal _expected = expectedItem.getQuantity();
        BigDecimal _diff = diffItem == null ? BigDecimal.ZERO : diffItem.getQuantity();

        setExpected(_expected);
        setDiff(_diff);
    }

    public BigDecimal getExpected() {
        return expected;
    }

    public void setExpected(BigDecimal expected) {
        if (expected == null)
            throw new NullPointerException("expected");
        this.expected = expected;
    }

    public BigDecimal getDiff() {
        return super.getQuantity();
    }

    public void setDiff(BigDecimal diff) {
        super.setQuantity(diff);
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

}
