package com.bee32.plover.orm.ext.dict;

import java.io.Serializable;

import javax.free.IVariantLookupMap;
import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.orm.util.EntityDto;

public class DictDto<E extends DictEntity<K>, K extends Serializable>
        extends EntityDto<E, K> {

    private static final long serialVersionUID = 1L;

    protected String name;
    protected String description;
    protected String icon;

    public DictDto() {
        super();
    }

    public DictDto(E source) {
        super(source);
    }

    @Override
    protected void _marshal(E source) {
        name = source.getName();
        description = source.getDescription();
        icon = source.getIcon();
    }

    @Override
    protected void _unmarshalTo(E target) {
        target.setName(name);
        target.setDescription(description);
        target.setIcon(icon);
    }

    @Override
    public void parse(IVariantLookupMap<String> map)
            throws ParseException, TypeConvertException {
        super.parse(map);

        name = map.getString("name");
        description = map.getString("description");
        icon = map.getString("icon");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

}
