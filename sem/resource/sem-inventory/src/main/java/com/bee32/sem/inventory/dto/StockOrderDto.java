package com.bee32.sem.inventory.dto;

import com.bee32.sem.inventory.entity.StockOrder;

public class StockOrderDto
        extends AbstractStockOrderDto<StockOrder> {

    private static final long serialVersionUID = 1L;

    public StockOrderDto() {
        super();
    }

    public StockOrderDto(int fmask) {
        super(fmask);
    }

    @Override
    public StockOrderDto populate(Object source) {
        super.populate(source);
        return this;
    }

}
