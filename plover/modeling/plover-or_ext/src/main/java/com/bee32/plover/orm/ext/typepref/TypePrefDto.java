package com.bee32.plover.orm.ext.typepref;

import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.orm.util.IUnmarshalContext;

public abstract class TypePrefDto<E extends TypePrefEntity>
        extends EntityDto<E, String>
        implements ITypeAbbrAware {

    private static final long serialVersionUID = 1L;

    Class<?> type;
    String displayName;

    public TypePrefDto() {
        super();
    }

    public TypePrefDto(E source) {
        super(source);
    }

    public TypePrefDto(int selection) {
        super(selection);
    }

    public TypePrefDto(E source, int selection) {
        super(source, selection);
    }

    protected void __marshal(E source) {
        super.__marshal(source);
        type = source.getType();
        displayName = source.getDisplayName();
    }

    protected void __unmarshalTo(IUnmarshalContext context, E target) {
        super.__unmarshalTo(context, target);
    }

    public Class<?> getType() {
        return type;
    }

    public String getDisplayName() {
        return displayName;
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
