package com.bee32.sem.misc;

import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.EntityDto;

public class UnmarshalMap
        extends CastLinkedMap<Entity<?>, EntityDto<?, ?>> {

    private static final long serialVersionUID = 1L;

    String label;
    Class<?> entityClass = Entity.class;

    Map<String, UnmarshalMap> subMaps;

    public UnmarshalMap() {
        super(new IdentityHashMap<Entity<?>, EntityDto<?, ?>>());
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<?> entityClass) {
        if (entityClass == null)
            throw new NullPointerException("entityClass");
        this.entityClass = entityClass;
    }

    public <E extends Entity<?>> Set<E> entitySet() {
        return _keySet();
    }

    public <D extends EntityDto<?, ?>> Collection<D> dtos() {
        return _values();
    }

    public <D extends EntityDto<?, ?>> D getSourceDto(Entity<?> entity) {
        return _get(entity);
    }

    public Map<String, UnmarshalMap> getSubMaps() {
        if (subMaps == null) {
            synchronized (this) {
                if (subMaps == null) {
                    subMaps = new LinkedHashMap<String, UnmarshalMap>();
                }
            }
        }
        return subMaps;
    }

    public UnmarshalMap getSubMap(String id) {
        Map<String, UnmarshalMap> subMaps = getSubMaps();
        UnmarshalMap subMap = subMaps.get(id);
        if (subMap == null) {
            synchronized (this) {
                if (subMap == null) {
                    subMap = new UnmarshalMap();
                    subMaps.put(id, subMap);
                }
            }
        }
        return subMap;
    }

}
