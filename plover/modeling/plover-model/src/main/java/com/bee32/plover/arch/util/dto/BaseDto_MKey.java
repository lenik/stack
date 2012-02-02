package com.bee32.plover.arch.util.dto;

public class BaseDto_MKey {

    final Object source;
    final Class<?> dtoType;
    final MarshalType marshalType;

    public BaseDto_MKey(Object source, Class<?> dtoType, MarshalType marshalType) {
        if (source == null)
            throw new NullPointerException("source");
        if (dtoType == null)
            throw new NullPointerException("dtoType");
        if (marshalType == null)
            throw new NullPointerException("marshalType");
        this.source = source;
        this.dtoType = dtoType;
        this.marshalType = marshalType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += source.hashCode();
        hash += dtoType.hashCode();
        // hash += marshalType.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        BaseDto_MKey o = (BaseDto_MKey) obj;
        if (!source.equals(o.source))
            return false;
        if (!dtoType.equals(o.dtoType))
            return false;
        // if (marshalType != o.marshalType)
        // return false;
        return true;
    }

}
