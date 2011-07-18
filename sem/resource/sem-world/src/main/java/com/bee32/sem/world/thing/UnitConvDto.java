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

    UnitConvDto parent;
    UnitDto unit;
    Map<UnitDto, Double> ratioMap;

    public UnitConvDto() {
        super();
    }

    public UnitConvDto(int selection) {
        super(selection);
    }

    @Override
    protected void _marshal(UnitConv source) {
        parent = mref(UnitConvDto.class, source.getParent());
        unit = mref(UnitDto.class, source.getUnit());

        if (selection.contains(MAP)) {
            ratioMap = new HashMap<UnitDto, Double>();
            for (Entry<Unit, Double> entry : source.getRatioMap().entrySet()) {
                UnitDto unit = mref(UnitDto.class, entry.getKey());
                double ratio = entry.getValue();
                ratioMap.put(unit, ratio);
            }
        }
    }

    @Override
    protected void _unmarshalTo(UnitConv target) {
        merge(target, "parent", parent);
        merge(target, "unit", unit);

        if (selection.contains(MAP)) {
            Map<Unit, Double> _ratioMap = new HashMap<Unit, Double>();
            for (Entry<UnitDto, Double> entry : ratioMap.entrySet()) {
                Unit _unit = entry.getKey().unmarshal(getSession());
                double ratio = entry.getValue();
                _ratioMap.put(_unit, ratio);
            }
            target.setRatioMap(_ratioMap);
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

    public UnitConvDto getParent() {
        return parent;
    }

    public void setParent(UnitConvDto parent) {
        this.parent = parent;
    }

    public UnitDto getUnit() {
        return unit;
    }

    public void setUnit(UnitDto unit) {
        this.unit = unit;
    }

    public Map<UnitDto, Double> getRatioMap() {
        return ratioMap;
    }

    public void setRatioMap(Map<UnitDto, Double> ratioMap) {
        this.ratioMap = ratioMap;
    }

}
