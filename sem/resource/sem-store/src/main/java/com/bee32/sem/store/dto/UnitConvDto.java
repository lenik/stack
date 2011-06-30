package com.bee32.sem.store.dto;

import java.util.Map;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.ext.dict.NameDictDto;
import com.bee32.sem.store.entity.UnitConv;

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
            // marshal map.
        }
    }

    @Override
    protected void _unmarshalTo(UnitConv target) {
        merge(target, "from", from);

        if (selection.contains(MAP)) {
            // ....
        }
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        from = new UnitDto().ref(map.getString("from"));

        if (selection.contains(MAP)) {
            // TODO
        }
    }

}
