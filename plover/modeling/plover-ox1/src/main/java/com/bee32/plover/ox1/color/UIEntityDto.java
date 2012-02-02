package com.bee32.plover.ox1.color;

import java.io.Serializable;

import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.ox1.c.CEntityDto;
import com.bee32.plover.util.TextUtil;

public abstract class UIEntityDto<E extends UIEntity<K>, K extends Serializable>
        extends CEntityDto<E, K> {

    private static final long serialVersionUID = 1L;

    protected String label;
    protected String description;

    public UIEntityDto() {
        super();
    }

    public UIEntityDto(int selection) {
        super(selection);
    }

    @Override
    public UIEntityDto<E, K> populate(Object source) {
        if (source instanceof UIEntityDto<?, ?>) {
            UIEntityDto<?, ?> o = (UIEntityDto<?, ?>) source;
            _populate(o);
        } else
            super.populate(source);
        return this;
    }

    protected void _populate(UIEntityDto<?, ?> o) {
        super._populate(o);
        label = o.label;
        description = o.description;
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

    @NLength(min = 1, max = UIEntity.LABEL_LENGTH)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = TextUtil.normalizeSpace(label);
    }

    @NLength(max = UIEntity.DESCRIPTION_LENGTH)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = TextUtil.normalizeSpace(description);
    }

}
