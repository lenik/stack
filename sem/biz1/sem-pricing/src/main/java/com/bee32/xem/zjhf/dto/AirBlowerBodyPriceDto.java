package com.bee32.xem.zjhf.dto;

import java.math.BigDecimal;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.xem.zjhf.entity.AirBlowerBodyPrice;

public class AirBlowerBodyPriceDto extends UIEntityDto<AirBlowerBodyPrice, Long> {

    private static final long serialVersionUID = 1L;

    AirBlowerTypeDto type;
    String number;
    BigDecimal value;


    @Override
    protected void _marshal(AirBlowerBodyPrice source) {
        type = mref(AirBlowerTypeDto.class, source.getType());
        number = source.getNumber();
        value = source.getValue();
    }

    @Override
    protected void _unmarshalTo(AirBlowerBodyPrice target) {
        merge(target, "type", type);
        target.setNumber(number);
        target.setValue(value);
    }

    @Override
    protected void _parse(TextMap map) throws ParseException {
    }

    public AirBlowerTypeDto getType() {
        return type;
    }

    public void setType(AirBlowerTypeDto type) {
        this.type = type;
    }

    @NLength(max = AirBlowerBodyPrice.NUMBER_LENGTH)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }




}
