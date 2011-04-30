package com.bee32.plover.orm.ext.meta;

import java.util.List;

import javax.free.ParseException;

import com.bee32.plover.arch.util.PropertyAccessor;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.ext.typepref.TypePrefDto;
import com.bee32.plover.orm.util.IUnmarshalContext;

public class EntityInfoDto
        extends TypePrefDto<EntityInfo> {

    private static final long serialVersionUID = 1L;

    public static final int COLUMNS = 1;

    String nameOtf;
    String alias;
    String description;

    List<EntityColumnDto> columns;

    public EntityInfoDto() {
        super(COLUMNS);
    }

    public EntityInfoDto(EntityInfo source) {
        super(COLUMNS, source);
    }

    public EntityInfoDto(int selection) {
        super(selection);
    }

    public EntityInfoDto(int selection, EntityInfo source) {
        super(selection, source);
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
    protected void _unmarshalTo(IUnmarshalContext context, EntityInfo target) {
        target.setNameOtf(nameOtf);
        target.setAlias(alias);
        target.setDescription(description);

        if (selection.contains(COLUMNS))
            with(context, target).unmarshalList(columnsProperty, columns);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        nameOtf = map.getString("nameOtf");
        alias = map.getString("alias");
        description = map.getString("description");
    }

    static final PropertyAccessor<EntityInfo, List<EntityColumn>> columnsProperty = new PropertyAccessor<EntityInfo, List<EntityColumn>>(
            List.class) {

        @Override
        public List<EntityColumn> get(EntityInfo entity) {
            return entity.getColumns();
        }

        @Override
        public void set(EntityInfo entity, List<EntityColumn> columns) {
            entity.setColumns(columns);
        }

    };

}
