package com.bee32.plover.orm.ext.meta;

import java.util.List;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.ext.typePref.TypePrefDto;

public class EntityInfoDto
        extends TypePrefDto<EntityInfo> {

    private static final long serialVersionUID = 1L;

    public static final int COLUMNS = 1;

    String nameOtf;
    String alias;
    String description;

    List<EntityColumnDto> columns;

    public EntityInfoDto() {
        super();
    }

    public EntityInfoDto(int selection) {
        super(selection);
    }

    @Override
    protected void _marshal(EntityInfo source) {
        nameOtf = source.getNameOtf();
        alias = source.getAlias();
        description = source.getDescription();

        if (selection.contains(COLUMNS))
            columns = marshalList(EntityColumnDto.class, source.getColumns());
    }

    @Override
    protected void _unmarshalTo(EntityInfo target) {
        target.setNameOtf(nameOtf);
        target.setAlias(alias);
        target.setDescription(description);

        if (selection.contains(COLUMNS))
            mergeList(target, "columns", columns);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        nameOtf = map.getString("nameOtf");
        alias = map.getString("alias");
        description = map.getString("description");
    }

}
