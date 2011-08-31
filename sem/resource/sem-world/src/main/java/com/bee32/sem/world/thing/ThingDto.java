package com.bee32.sem.world.thing;

import java.io.Serializable;

import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.arch.util.DummyId;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.xp.EntityExtDto;
import com.bee32.plover.ox1.xp.XPool;

public abstract class ThingDto<E extends Thing<X>, X extends XPool<?>>
        extends EntityExtDto<E, Long, X> {

    private static final long serialVersionUID = 1L;

    public static final int CONV_MAP = 1;

    String serial;

    UnitDto unit;
    String unitHint;

    UnitConvDto unitConv;

    public ThingDto() {
        super();
    }

    public ThingDto(int selection) {
        super(selection);
    }

    @Override
    protected void __marshal(E source) {
        super.__marshal(source);

        serial = source.getSerial();
        unitHint = source.getUnitHint();
        unit = mref(UnitDto.class, source.getUnit());

        int unitConvSelection = 0;
        if (selection.contains(CONV_MAP))
            unitConvSelection |= UnitConvDto.MAP;
        unitConv = mref(UnitConvDto.class, unitConvSelection, source.getUnitConv());
    }

    @Override
    protected void __unmarshalTo(E target) {
        super.__unmarshalTo(target);

        target.setSerial(serial);
        target.setUnitHint(unitHint);
        merge(target, "unit", unit);
        merge(target, "unitConv", unitConv);
    }

    @Override
    protected void __parse(TextMap map)
            throws ParseException, TypeConvertException {
        super.__parse(map);

        serial = map.getString("serial");

        unit = new UnitDto().ref(map.getString("unit"));
        unitConv = new UnitConvDto().ref(map.getString("unitConv"));
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        if (serial != null && serial.isEmpty())
            serial = null;
        this.serial = serial;
    }

    public UnitDto getUnit() {
        return unit;
    }

    public void setUnit(UnitDto unit) {
        if (unit == null)
            throw new NullPointerException("unit");
        this.unit = unit;
    }

    public String getUnitHint() {
        return unitHint;
    }

    public void setUnitHint(String unitHint) {
        this.unitHint = unitHint;
    }

    public UnitConvDto getUnitConv() {
        return unitConv;
    }

    public void setUnitConv(UnitConvDto unitConv) {
        this.unitConv = unitConv;
    }

    @Override
    protected Serializable naturalId() {
        if (serial == null)
            return new DummyId(this);
        else
            return serial;
    }

}
