package com.bee32.plover.orm.ext.dict;

import java.io.Serializable;

import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.util.IUnmarshalContext;

public abstract class DictEntityDto<E extends DictEntity<K>, K extends Serializable>
        extends EntityDto<E, K> {

    private static final long serialVersionUID = 1L;

    protected String alias;
    protected String description;
    protected String icon;

    public DictEntityDto() {
        super();
    }

    public DictEntityDto(E source) {
        super(source);
    }

    public DictEntityDto(int selection) {
        super(selection);
    }

    public DictEntityDto(int selection, E source) {
        super(selection, source);
    }

    @Override
    protected void __marshal(E source) {
        super.__marshal(source);
        alias = source.getAlias();
        description = source.getDescription();
        icon = source.getIcon();
    }

    @Override
    protected void __unmarshalTo(IUnmarshalContext context, E target) {
        super.__unmarshalTo(context, target);
        target.setAlias(alias);
        target.setDescription(description);
        target.setIcon(icon);
    }

    @Override
    protected void __parse(TextMap map)
            throws ParseException, TypeConvertException {
        super.__parse(map);
        alias = map.getString("displayName");
        description = map.getString("description");
        icon = map.getString("icon");
    }

    @Override
    protected void _marshal(E source) {
    }

    @Override
    protected void _unmarshalTo(IUnmarshalContext context, E target) {
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
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
