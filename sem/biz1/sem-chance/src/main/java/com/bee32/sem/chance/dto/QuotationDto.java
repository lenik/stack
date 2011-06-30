package com.bee32.sem.chance.dto;

import java.util.List;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.chance.entity.Quotation;

public class QuotationDto
        extends EntityDto<Quotation, Long> {

    private static final long serialVersionUID = 1L;

    private String material;
    private List<QuotationDetailDto> details;

    @Override
    protected void _marshal(Quotation source) {
        this.material = source.getMaterial();
        this.details = DTOs.marshalList(QuotationDetailDto.class, source.getDetails());
    }

    @Override
    protected void _unmarshalTo(Quotation target) {
        target.setMaterial(material);
        mergeList(target, "details", details);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

}
