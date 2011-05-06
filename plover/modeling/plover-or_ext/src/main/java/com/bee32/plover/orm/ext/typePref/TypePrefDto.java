package com.bee32.plover.orm.ext.typePref;

import javax.persistence.Transient;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.orm.util.IUnmarshalContext;

public abstract class TypePrefDto<E extends TypePrefEntity>
        extends EntityDto<E, String>
        implements ITypeAbbrAware {

    private static final long serialVersionUID = 1L;

    Class<?> type;

    public TypePrefDto() {
        super();
    }

    public TypePrefDto(E source) {
        super(source);
    }

    public TypePrefDto(int selection) {
        super(selection);
    }

    public TypePrefDto(int selection, E source) {
        super(selection, source);
    }

    protected void __marshal(E source) {
        super.__marshal(source);
        type = source.getType();
    }

    protected void __unmarshalTo(IUnmarshalContext context, E target) {
        super.__unmarshalTo(context, target);
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
