package com.bee32.sem.world.thing;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.ext.dict.NameDictDto;

public class UnitConvDto
        extends NameDictDto<UnitConv> {

    private static final long serialVersionUID = 1L;

    public static final int MAP = 1;

    UnitDto unit;
    Map<UnitDto, Double> scaleMap;

    public UnitConvDto() {
        super();
    }

    public UnitConvDto(int selection) {
        super(selection);
    }

    @Override
    protected void _marshal(UnitConv source) {
        unit = mref(UnitDto.class, source.getUnit());

        if (selection.contains(MAP)) {
            scaleMap = new HashMap<UnitDto, Double>();
            for (Entry<Unit, Double> entry : source.getScaleMap().entrySet()) {
                UnitDto scaleUnit = mref(UnitDto.class, entry.getKey());
                double scale = entry.getValue();
                scaleMap.put(scaleUnit, scale);
            }
        }
    }

    @Override
    protected void _unmarshalTo(UnitConv target) {
        merge(target, "unit", unit);

        if (selection.contains(MAP)) {
            Map<Unit, Double> _scaleMap = new HashMap<Unit, Double>();
            for (Entry<UnitDto, Double> entry : scaleMap.entrySet()) {
                Unit _unit = entry.getKey().unmarshal(getSession());
                double scale = entry.getValue();
                _scaleMap.put(_unit, scale);
            }
            target.setScaleMap(_scaleMap);
        }
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        unit = new UnitDto().ref(map.getString("unit"));

        if (selection.contains(MAP)) {
            throw new NotImplementedException();
        }
    }

    public UnitDto getUnit() {
        return unit;
    }

    public void setUnit(UnitDto unit) {
        if (unit == null)
            throw new NullPointerException("unit");
        this.unit = unit;
    }

    public Map<UnitDto, Double> getScaleMap() {
        return scaleMap;
    }

    public void setScaleMap(Map<UnitDto, Double> scaleMap) {
        if (scaleMap == null)
            throw new NullPointerException("scaleMap");
        this.scaleMap = scaleMap;
    }

}
