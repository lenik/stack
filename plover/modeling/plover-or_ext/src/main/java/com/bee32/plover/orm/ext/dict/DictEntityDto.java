package com.bee32.plover.orm.ext.dict;

import java.io.Serializable;

import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.arch.util.ParameterMap;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.util.IUnmarshalContext;

public class DictEntityDto<E extends DictEntity<K>, K extends Serializable>
        extends EntityDto<E, K> {

    private static final long serialVersionUID = 1L;

    protected String name;
    protected String displayName;
    protected String description;
    protected String icon;

    public DictEntityDto() {
        super();
    }

    public DictEntityDto(E source) {
        super(source);
    }

    @Override
    protected void _marshal(E source) {
        name = source.getName();
        displayName = source.getDisplayName();
        description = source.getDescription();
        icon = source.getIcon();
    }

    @Override
    protected void _unmarshalTo(IUnmarshalContext context, E target) {
        target.setName(name);
        target.setDisplayName(displayName);
        target.setDescription(description);
        target.setIcon(icon);
    }

    @Override
    public void _parse(ParameterMap map)
            throws ParseException, TypeConvertException {
        name = map.getString("name");
        displayName = map.getString("displayName");
        description = map.getString("description");
        icon = map.getString("icon");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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
