package com.bee32.sem.asset.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.asset.entity.FundFlow;

public class CreditNoteDto extends FundFlowDto {

    private static final long serialVersionUID = 1L;

    public CreditNoteDto() {
        super();
    }

    @Override
    protected void _marshal(FundFlow source) {
    }

    @Override
    protected void _unmarshalTo(FundFlow target) {
        super._unmarshalTo(target);
    }

    @Override
    protected void _parse(TextMap map) throws ParseException {
    }



}
