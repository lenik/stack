package com.bee32.plover.orm.ext.color;

import java.io.Serializable;

import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;

public abstract class UserFriendlyEntityDto<E extends UserFriendlyEntity<K>, K extends Serializable>
        extends EntityDto<E, K> {

    private static final long serialVersionUID = 1L;

    String label;
    String description;

    public UserFriendlyEntityDto() {
        super();
    }

    public UserFriendlyEntityDto(E source) {
        super(source);
    }

    public UserFriendlyEntityDto(int selection) {
        super(selection);
    }

    public UserFriendlyEntityDto(int selection, E source) {
        super(selection, source);
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
