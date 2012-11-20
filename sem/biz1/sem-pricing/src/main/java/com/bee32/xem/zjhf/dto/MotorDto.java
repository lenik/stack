package com.bee32.xem.zjhf.dto;

import java.math.BigDecimal;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.xem.zjhf.entity.Motor;

public class MotorDto extends UIEntityDto<Motor, Long> {

    private static final long serialVersionUID = 1L;

    MotorTypeDto type;
    String modelSpec;
    BigDecimal value;


    @Override
    protected void _marshal(Motor source) {
        type = mref(MotorTypeDto.class, source.getType());
        modelSpec = source.getModelSpec();
        value = source.getValue();
    }

    @Override
    protected void _unmarshalTo(Motor target) {
        merge(target, "type", type);
        target.setModelSpec(modelSpec);
        target.setValue(value);
    }

    @Override
    protected void _parse(TextMap map) throws ParseException {
    }

    public MotorTypeDto getType() {
        return type;
    }

    public void setType(MotorTypeDto type) {
        this.type = type;
    }

    @NLength(max = Motor.MODEL_SPEC_LENGTH)
    public String getModelSpec() {
        return modelSpec;
    }

    public void setModelSpec(String modelSpec) {
        this.modelSpec = modelSpec;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }




}
