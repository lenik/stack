package com.bee32.sem.thing.dto;

import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.ext.xp.EntityExtDto;
import com.bee32.plover.orm.ext.xp.XPool;
import com.bee32.plover.orm.util.IUnmarshalContext;
import com.bee32.sem.thing.entity.Thing;

public abstract class ThingDto<E extends Thing<X>, X extends XPool<?>>
        extends EntityExtDto<E, Long, X> {

    private static final long serialVersionUID = 1L;

    public static final int CONV_MAP = 1;

    String serial;
    String name;
    String description;

    String barCode;

    UnitDto unit;
    UnitConvDto unitConv;

    public ThingDto() {
        super();
    }

    public ThingDto(E source) {
        super(source);
    }

    public ThingDto(int selection) {
        super(selection);
    }

    public ThingDto(int selection, E source) {
        super(selection, source);
    }

    @Override
    protected void __marshal(E source) {
        super.__marshal(source);

        serial = source.getSerial();
        name = source.getName();
        description = source.getDescription();

        barCode = source.getBarCode();

        unit = new UnitDto(source.getUnit());

        int unitConvSelection = 0;
        if (selection.contains(CONV_MAP))
            unitConvSelection |= UnitConvDto.MAP;
        unitConv = new UnitConvDto(unitConvSelection, source.getUnitConv());
    }

    @Override
    protected void __unmarshalTo(IUnmarshalContext context, E target) {
        super.__unmarshalTo(context, target);

        target.setSerial(serial);
        target.setName(name);
        target.setDescription(description);

        target.setBarCode(barCode);

        with(context, target).unmarshal("unit", unit);
        with(context, target).unmarshal("unitConv", unitConv);
    }

    @Override
    protected void __parse(TextMap map)
            throws ParseException, TypeConvertException {
        super.__parse(map);

        serial = map.getString("serial");
        name = map.getString("name");
        description = map.getString("description");

        barCode = map.getString("barCode");

        unit = new UnitDto().ref(map.getString("unit"));
        unitConv = new UnitConvDto().ref(map.getString("unitConv"));
    }

}
