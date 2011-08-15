package com.bee32.sem.inventory.dto.tx;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.tx.entity.StockTaking;

public class StockTakingDto
        extends StockJobDto<StockTaking> {

    private static final long serialVersionUID = 1L;

    StockOrderDto expected;
    StockOrderDto actual;
    StockOrderDto adjustment;

    public StockTakingDto() {
        super();
    }

    public StockTakingDto(int selection) {
        super(selection);
    }

    @Override
    protected void _marshal(StockTaking source) {
        expected = marshal(StockOrderDto.class, source.getExpected());
        actual = marshal(StockOrderDto.class, source.getActual());
        adjustment = marshal(StockOrderDto.class, source.getAdjustment());
    }

    @Override
    protected void _unmarshalTo(StockTaking target) {
        merge(target, "expected", expected);
        merge(target, "actual", actual);
        merge(target, "adjustment", adjustment);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public StockOrderDto getExpected() {
        return expected;
    }

    public void setExpected(StockOrderDto expected) {
        if (expected == null)
            throw new NullPointerException("expected");
        this.expected = expected;
    }

    public StockOrderDto getActual() {
        return actual;
    }

    public void setActual(StockOrderDto actual) {
        if (actual == null)
            throw new NullPointerException("actual");
        this.actual = actual;
    }

    public StockOrderDto getAdjustment() {
        return adjustment;
    }

    public void setAdjustment(StockOrderDto adjustment) {
        if (adjustment == null)
            throw new NullPointerException("adjustment");
        this.adjustment = adjustment;
    }

}
