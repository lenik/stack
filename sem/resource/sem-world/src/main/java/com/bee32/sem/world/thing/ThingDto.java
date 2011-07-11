package com.bee32.sem.world.thing;

import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.ext.xp.EntityExtDto;
import com.bee32.plover.orm.ext.xp.XPool;

public abstract class ThingDto<E extends Thing<X>, X extends XPool<?>>
        extends EntityExtDto<E, Long, X> {

    private static final long serialVersionUID = 1L;

    public static final int CONV_MAP = 1;

    String name;

    UnitDto unit;
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

        name = source.getName();

        unit = mref(UnitDto.class, source.getUnit());

        int unitConvSelection = 0;
        if (selection.contains(CONV_MAP))
            unitConvSelection |= UnitConvDto.MAP;
        unitConv = mref(UnitConvDto.class, unitConvSelection, source.getUnitConv());
    }

    @Override
    protected void __unmarshalTo(E target) {
        super.__unmarshalTo(target);

        target.setName(name);

        merge(target, "unit", unit);
        merge(target, "unitConv", unitConv);
    }

    @Override
    protected void __parse(TextMap map)
            throws ParseException, TypeConvertException {
        super.__parse(map);

        name = map.getString("name");

        unit = new UnitDto().ref(map.getString("unit"));
        unitConv = new UnitConvDto().ref(map.getString("unitConv"));
    }

}
