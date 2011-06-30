package com.bee32.sem.chance.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.chance.entity.Quotation;

public class QuotationDto
        extends EntityDto<Quotation, Long> {

    private static final long serialVersionUID = 1L;

    @Override
    protected void _marshal(Quotation source) {
    }

    @Override
    protected void _unmarshalTo(Quotation target) {
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

}
