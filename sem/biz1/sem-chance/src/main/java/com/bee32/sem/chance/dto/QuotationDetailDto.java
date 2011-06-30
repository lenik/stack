package com.bee32.sem.chance.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.chance.entity.QuotationDetail;

public class QuotationDetailDto
        extends EntityDto<QuotationDetail, Long> {

    private static final long serialVersionUID = 1L;

    @Override
    protected void _marshal(QuotationDetail source) {
    }

    @Override
    protected void _unmarshalTo(QuotationDetail target) {
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

}
