package com.bee32.sem.world.thing;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.dict.SimpleNameDictDto;

public class UnitDto
        extends SimpleNameDictDto<Unit> {

    private static final long serialVersionUID = 1L;
    UnitDto stdUnit;
    double scale;

    @Override
    protected void _marshal(Unit source) {
        super._marshal(source);
        stdUnit = mref(UnitDto.class, source.getStdUnit());
        if (source.getScale() != null)
            scale = source.getScale();
    }

    @Override
    protected void _unmarshalTo(Unit target) {
        super._unmarshalTo(target);
        merge(target, "stdUnit", stdUnit);
        target.setScale(scale);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        super._parse(map);
    }

    public UnitDto getStdUnit() {
        return stdUnit;
    }

    public void setStdUnit(UnitDto stdUnit) {
        this.stdUnit = stdUnit;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

}
