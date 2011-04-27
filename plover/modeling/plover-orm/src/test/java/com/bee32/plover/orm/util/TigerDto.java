package com.bee32.plover.orm.util;

import javax.free.ParseException;

import com.bee32.plover.arch.util.ParameterMap;
import com.bee32.plover.orm.feaCat.Tiger;

public class TigerDto
        extends EntityDto<Tiger, Long> {

    private static final long serialVersionUID = 1L;

    String name;
    String color;
    int power;

    public TigerDto() {
        super();
    }

    public TigerDto(Tiger entity) {
        super(entity);
    }

    @Override
    protected void _marshal(Tiger entity) {
        this.name = entity.getName();
        this.color = entity.getColor();
        this.power = entity.getPower();
    }

    @Override
    protected void _unmarshalTo(IUnmarshalContext context, Tiger entity) {
        entity.setName(name);
        entity.setColor(color);
        entity.setPower(power);
    }

    @Override
    protected void _parse(ParameterMap map)
            throws ParseException {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

}
