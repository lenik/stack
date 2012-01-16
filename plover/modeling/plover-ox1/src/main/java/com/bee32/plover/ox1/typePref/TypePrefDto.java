package com.bee32.plover.ox1.typePref;

import javax.persistence.Transient;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.ox1.c.CEntityDto;

public abstract class TypePrefDto<E extends TypePrefEntity>
        extends CEntityDto<E, String>
        implements ITypeAbbrAware {

    private static final long serialVersionUID = 1L;

    Class<?> type;

    public TypePrefDto() {
        super();
    }

    public TypePrefDto(int selection) {
        super(selection);
    }

    @Override
    protected void __marshal(E source) {
        super.__marshal(source);
        type = source.getType(); // Transient.
    }

    @Override
    protected void __unmarshalTo(E target) {
        super.__unmarshalTo(target);
        // Transient.: target.setType(type);
    }

    public String getTypeId() {
        return getId();
    }

    public void setTypeId(String typeId) {
        setId(typeId);
        try {
            type = ABBR.expand(typeId);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
        setTypeId(ABBR.abbr(type));
    }

    @Transient
    public String getDisplayName() {
        if (type == null)
            return "(n/a)";
        else
            return ClassUtil.getTypeName(type);
    }

    public String getTypeName() {
        return type == null ? "" : type.getCanonicalName();
    }

    public void setTypeName(String typeName)
            throws ClassNotFoundException {
        if (typeName == null)
            type = null;
        else {
            typeName = typeName.trim();
            type = ABBR.expand(typeName);
        }
    }

}
