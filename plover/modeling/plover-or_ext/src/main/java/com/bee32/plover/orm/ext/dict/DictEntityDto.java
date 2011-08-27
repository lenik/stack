package com.bee32.plover.orm.ext.dict;

import java.io.Serializable;

import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.ext.c.CEntityDto;

public abstract class DictEntityDto<E extends DictEntity<K>, K extends Serializable>
        extends CEntityDto<E, K> {

    private static final long serialVersionUID = 1L;

    protected String label;
    protected String description;

    public DictEntityDto() {
        super();
    }

    public DictEntityDto(int selection) {
        super(selection);
    }

    @Override
    protected void __marshal(E source) {
        super.__marshal(source);
        label = source.getLabel();
        description = source.getDescription();
    }

    @Override
    protected void __unmarshalTo(E target) {
        super.__unmarshalTo(target);
        target.setLabel(label);
        target.setDescription(description);
    }

    @Override
    protected void __parse(TextMap map)
            throws ParseException, TypeConvertException {
        super.__parse(map);
        label = map.getString("label");
        description = map.getString("description");
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

}
