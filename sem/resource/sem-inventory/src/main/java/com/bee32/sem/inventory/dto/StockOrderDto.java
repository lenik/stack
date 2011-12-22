package com.bee32.sem.inventory.dto;

import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.process.StockOrderVerifySupportDto;

public class StockOrderDto extends AbstractStockOrderDto<StockOrder> {

    private static final long serialVersionUID = 1L;

    StockOrderVerifySupportDto stockOrderVerifySupport;

    public StockOrderDto() {
        super();
    }

    public StockOrderDto(int selection) {
        super(selection);
    }

    @Override
    public StockOrderDto populate(Object source) {
        super.populate(source);
        return this;
    }

    @Override
    protected void _marshal(StockOrder source) {
        super._marshal(source);
        stockOrderVerifySupport = marshal(StockOrderVerifySupportDto.class, source.getVerifyContext());
    }

    @Override
    protected void _unmarshalTo(StockOrder target) {
        super._unmarshalTo(target);
        merge(target, "verifyContext", stockOrderVerifySupport);
    }

    public StockOrderVerifySupportDto getVerifyContext() {
        return stockOrderVerifySupport;
    }

    public void setVerifyContext(
            StockOrderVerifySupportDto stockOrderVerifySupport) {
        this.stockOrderVerifySupport = stockOrderVerifySupport;
    }
}
