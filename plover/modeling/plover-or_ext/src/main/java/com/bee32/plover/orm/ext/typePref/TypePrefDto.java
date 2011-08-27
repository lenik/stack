package com.bee32.plover.orm.ext.typePref;

import javax.persistence.Transient;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.orm.util.CEntityDto;
import com.bee32.plover.orm.util.ITypeAbbrAware;

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

    protected void __marshal(E source) {
        super.__marshal(source);
        type = source.getType(); // Transient.
    }

    protected void __unmarshalTo(E target) {
        super.__unmarshalTo(target);
        // Transient.: target.setType(type);
    }

    public Class<?> getType() {
        return type;
    }

    @Transient
    public String getDisplayName() {
        if (type == null)
            return null;
        return ClassUtil.getDisplayName(type);
    }

    public String getTypeName() {
        return type == null ? "" : type.getName();
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
