package com.bee32.sem.thing.dto;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.ext.dict.NameDictDto;
import com.bee32.sem.thing.entity.Unit;
import com.bee32.sem.thing.entity.UnitConv;

public class UnitConvDto
        extends NameDictDto<UnitConv> {

    private static final long serialVersionUID = 1L;

    public static final int MAP = 1;

    UnitDto from;
    Map<UnitDto, Double> ratioMap;

    public UnitConvDto() {
        super();
    }

    public UnitConvDto(int selection) {
        super(selection);
    }

    @Override
    protected void _marshal(UnitConv source) {
        from = mref(UnitDto.class, source.getFrom());

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
        merge(target, "from", from);

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
        from = new UnitDto().ref(map.getString("from"));

        if (selection.contains(MAP)) {
            throw new NotImplementedException();
        }
    }

}
