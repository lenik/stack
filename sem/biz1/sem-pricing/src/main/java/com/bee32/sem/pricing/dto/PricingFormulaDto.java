package com.bee32.sem.pricing.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.pricing.entity.PricingFormula;

public class PricingFormulaDto extends UIEntityDto<PricingFormula, Long> {

    private static final long serialVersionUID = 1L;

    String content;


    @Override
    protected void _marshal(PricingFormula source) {
        content = source.getContent();
    }

    @Override
    protected void _unmarshalTo(PricingFormula target) {
        target.setContent(content);
    }

    @Override
    protected void _parse(TextMap map) throws ParseException {
    }

    @NLength(max = PricingFormula.CONTENT_LENGTH)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        if (content == null)
            throw new NullPointerException("content");
        this.content = content;
    }


}
