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

    protected String label;
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
        label = source.getLabel();
        description = source.getDescription();
        icon = source.getIcon();
    }

    @Override
    protected void __unmarshalTo(IUnmarshalContext context, E target) {
        super.__unmarshalTo(context, target);
        target.setLabel(label);
        target.setDescription(description);
        target.setIcon(icon);
    }

    @Override
    protected void __parse(TextMap map)
            throws ParseException, TypeConvertException {
        super.__parse(map);
        label = map.getString("label");
        description = map.getString("description");
        icon = map.getString("icon");
    }

    @Override
    protected void _marshal(E source) {
        // Keep this as empty.
    }

    @Override
    protected void _unmarshalTo(IUnmarshalContext context, E target) {
        // Keep this as empty.
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        // Keep this as empty.
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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
