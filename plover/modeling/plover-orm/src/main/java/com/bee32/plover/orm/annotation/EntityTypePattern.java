package com.bee32.plover.orm.annotation;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

import javax.free.Nullables;

public class EntityTypePattern
        implements Serializable {

    private static final long serialVersionUID = 1L;

    final Class<?> entityType;
    final Map<String, String> typeParameters;

    public EntityTypePattern(Class<?> entityType) {
        this(entityType, null);
    }

    public EntityTypePattern(Class<?> entityType, Map<String, String> typeParameters) {
        if (entityType == null)
            throw new NullPointerException("entityType");
        this.entityType = entityType;
        this.typeParameters = typeParameters;
    }

    public Class<?> getEntityType() {
        return entityType;
    }

    public Map<String, String> getTypeParameters() {
        if (typeParameters == null)
            return Collections.emptyMap();
        else
            return Collections.unmodifiableMap(typeParameters);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof EntityTypePattern))
            return false;
        EntityTypePattern o = (EntityTypePattern) obj;
        if (!entityType.equals(o.entityType))
            return false;
        if (!Nullables.equals(typeParameters, o.typeParameters))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = entityType.hashCode();
        if (typeParameters != null)
            hash = hash * 17 + typeParameters.hashCode();
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(entityType.getCanonicalName());
        if (typeParameters != null)
            for (Entry<String, String> parameter : typeParameters.entrySet()) {
                String name = parameter.getKey();
                String value = parameter.getValue();
                buf.append(" " + name + "=" + value);
            }
        return buf.toString();
    }

}
