package com.bee32.sem.inventory.tx.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.inventory.tx.entity.SimpleStockJob;

public class SimpleStockJobDto
        extends StockJobDto<SimpleStockJob> {

    private static final long serialVersionUID = 1L;

    public SimpleStockJobDto() {
        super();
    }

    public SimpleStockJobDto(int fmask) {
        super(fmask);
    }

    @Override
    protected void _marshal(SimpleStockJob source) {
    }

    @Override
    protected void _unmarshalTo(SimpleStockJob target) {
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

}
