package com.bee32.sem.asset.dto;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.asset.entity.AccountSubject;
import com.bee32.sem.asset.entity.AccountTicketItem;

public class StockPurchaseDto
        extends StockTradeDto {

    private static final long serialVersionUID = 1L;

    public StockPurchaseDto() {
        this.setSubject(DTOs.marshal(AccountSubjectDto.class, AccountSubject.s2121));
        this.setDebitSide(true);
    }

    @Override
    protected void _marshal(AccountTicketItem source) {
        super._marshal(source);

        // StockPurchase _source = (StockPurchase) source;
    }

    @Override
    protected void _unmarshalTo(AccountTicketItem target) {
        super._unmarshalTo(target);

        // StockPurchase _target = (StockPurchase) target;
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

}
