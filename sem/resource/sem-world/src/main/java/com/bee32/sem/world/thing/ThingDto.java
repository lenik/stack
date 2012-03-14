package com.bee32.sem.world.thing;

import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.orm.entity.CopyUtils;
import com.bee32.plover.ox1.xp.EntityExtDto;
import com.bee32.plover.ox1.xp.XPool;
import com.bee32.plover.util.TextUtil;
import com.bee32.sem.world.color.NaturalColor;

public abstract class ThingDto<E extends Thing<X>, X extends XPool<?>>
        extends EntityExtDto<E, Long, X> {

    private static final long serialVersionUID = 1L;

    public static final int CONV_MAP = 0x00010000;

    String serial;
    String barCode;
    UnitDto unit;
    String unitHint;
    UnitConvDto unitConv;
    String modelSpec;
    NaturalColor color;

    public ThingDto() {
        super();
    }

    public ThingDto(int fmask) {
        super(fmask);
    }

    @Override
    protected void __copy() {
        super.__copy();
        // unitConv = CopyUtils.copy(unitConv);
        color = CopyUtils.clone(color);
    }

    @Override
    protected void __marshal(E source) {
        super.__marshal(source);
        serial = source.getSerial();
        barCode = source.getBarCode();
        unitHint = source.getUnitHint();
        unit = mref(UnitDto.class, source.getUnit());

        int unitConvSelection = 0;
        if (selection.contains(CONV_MAP))
            unitConvSelection |= UnitConvDto.MAP;
        unitConv = mref(UnitConvDto.class, unitConvSelection, source.getUnitConv());

        modelSpec = source.getModelSpec();
        color = source.getColor();
    }

    @Override
    protected void __unmarshalTo(E target) {
        super.__unmarshalTo(target);
        target.setSerial(serial);
        target.setBarCode(barCode);
        target.setUnitHint(unitHint);
        merge(target, "unit", unit);
        merge(target, "unitConv", unitConv);
        target.setModelSpec(modelSpec);
        target.setColor(color);
    }

    @Override
    protected void __parse(TextMap map)
            throws ParseException, TypeConvertException {
        super.__parse(map);

        serial = map.getString("serial");

        unit = new UnitDto().ref(map.getString("unit"));
        unitConv = new UnitConvDto().ref(map.getLong("unitConv.id"));
    }

    @NLength(max = Thing.SERIAL_LENGTH)
    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        serial = TextUtil.normalizeSpace(serial, true);
        this.serial = serial;
    }

    @NLength(max = Thing.BARCODE_LENGTH)
    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = TextUtil.normalizeSpace(barCode);
    }

    public UnitDto getUnit() {
        return unit;
    }

    public void setUnit(UnitDto unit) {
        if (unit == null)
            throw new NullPointerException("unit");
        this.unit = unit;
    }

    @NLength(max = Thing.UNIT_HINT_LENGTH)
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

    @NLength(max = Thing.MODELSPEC_LENGTH)
    public String getModelSpec() {
        return modelSpec;
    }

    public void setModelSpec(String modelSpec) {
        this.modelSpec = TextUtil.normalizeSpace(modelSpec);
    }

    public NaturalColor getColor() {
        return color;
    }

    public void setColor(NaturalColor color) {
        this.color = color;
    }

}
