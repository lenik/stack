package com.bee32.xem.zjhf.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.pricing.dto.PricingFormulaDto;
import com.bee32.sem.pricing.entity.PricingFormula;
import com.bee32.xem.zjhf.entity.ValvePricingFormula;

public class ValvePricingFormulaDto extends PricingFormulaDto {

    private static final long serialVersionUID = 1L;

    ValveTypeDto type;

    @Override
    protected void _marshal(PricingFormula source) {
        super._marshal(source);

        ValvePricingFormula f = (ValvePricingFormula)source;
        type = mref(ValveTypeDto.class, f.getType());
    }

    @Override
    protected void _unmarshalTo(PricingFormula target) {
        super._unmarshalTo(target);

        ValvePricingFormula f = (ValvePricingFormula)target;
        merge(f, "type", type);
    }

    @Override
    protected void _parse(TextMap map) throws ParseException {
        super._parse(map);
    }

    public ValveTypeDto getType() {
        return type;
    }

    public void setType(ValveTypeDto type) {
        this.type = type;
    }

}
