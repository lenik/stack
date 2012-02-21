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

    Map<String, UnmarshalMap> deltaMaps;

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

    public Map<String, UnmarshalMap> getDeltaMaps() {
        if (deltaMaps == null) {
            synchronized (this) {
                if (deltaMaps == null) {
                    deltaMaps = new LinkedHashMap<String, UnmarshalMap>();
                }
            }
        }
        return deltaMaps;
    }

    public UnmarshalMap deltaMap(String id) {
        Map<String, UnmarshalMap> deltaMaps = getDeltaMaps();
        UnmarshalMap deltaMap = deltaMaps.get(id);
        if (deltaMap == null) {
            synchronized (this) {
                if (deltaMap == null) {
                    deltaMap = new UnmarshalMap();
                    deltaMaps.put(id, deltaMap);
                }
            }
        }
        return deltaMap;
    }

}
