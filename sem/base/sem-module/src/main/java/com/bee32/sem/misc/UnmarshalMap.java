package com.bee32.sem.misc;

import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Set;

import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.EntityDto;

public class UnmarshalMap
        extends CastLinkedMap<Entity<?>, EntityDto<?, ?>> {

    private static final long serialVersionUID = 1L;

    public UnmarshalMap() {
        super(new IdentityHashMap<Entity<?>, EntityDto<?, ?>>());
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

}
