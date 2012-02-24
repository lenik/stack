package com.bee32.sem.asset.dto;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.asset.entity.AccountTicketItem;

public class StockSaleDto
        extends StockTradeDto {

    private static final long serialVersionUID = 1L;

    public StockSaleDto() {
        super();
    }

    public StockSaleDto(int fmask) {
        super(fmask);
    }

    @Override
    protected void _marshal(AccountTicketItem source) {
        super._marshal(source);

        // StockSale _source = (StockSale) source;
    }

    @Override
    protected void _unmarshalTo(AccountTicketItem target) {
        super._unmarshalTo(target);

        // StockSale _target = (StockSale) target;

    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }
}
