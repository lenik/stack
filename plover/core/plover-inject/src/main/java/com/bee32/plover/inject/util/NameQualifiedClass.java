package com.bee32.plover.inject.util;

import java.io.Serializable;

import javax.free.Nullables;

public class NameQualifiedClass
        implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Class<?> clazz;
    private final String qualifier;

    public NameQualifiedClass(Class<?> clazz, String qualifier) {
        this.clazz = clazz;
        this.qualifier = qualifier;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public String getQualifier() {
        return qualifier;
    }

    @Override
    public int hashCode() {
        int hash = 502359;
        if (clazz != null)
            hash += clazz.hashCode();
        if (qualifier != null)
            hash += qualifier.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof NameQualifiedClass))
            return false;

        NameQualifiedClass that = (NameQualifiedClass) obj;

        if (!Nullables.equals(this.clazz, that.clazz))
            return false;

        if (!Nullables.equals(this.qualifier, that.qualifier))
            return false;

        return true;
    }

    @Override
    public String toString() {
        return clazz + " : " + qualifier;
    }

}
