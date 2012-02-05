package com.bee32.plover.ox1.meta;

import java.util.List;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.typePref.TypePrefDto;

public class EntityInfoDto
        extends TypePrefDto<EntityInfo> {

    private static final long serialVersionUID = 1L;

    public static final int COLUMNS = 1;

    String nameOtf;
    String label;
    String description;

    List<EntityColumnDto> columns;

    public EntityInfoDto() {
        super();
    }

    public EntityInfoDto(int fmask) {
        super(fmask);
    }

    @Override
    protected void _marshal(EntityInfo source) {
        nameOtf = source.getNameOtf();
        label = source.getLabel();
        description = source.getDescription();

        if (selection.contains(COLUMNS))
            columns = marshalList(EntityColumnDto.class, source.getColumns());
    }

    @Override
    protected void _unmarshalTo(EntityInfo target) {
        target.setNameOtf(nameOtf);
        target.setLabel(label);
        target.setDescription(description);

        if (selection.contains(COLUMNS))
            mergeList(target, "columns", columns);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        nameOtf = map.getString("nameOtf");
        label = map.getString("label");
        description = map.getString("description");
    }

}
