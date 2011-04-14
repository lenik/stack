package com.bee32.plover.orm.util;

import java.io.Serializable;

import com.bee32.plover.arch.util.DataTransferObject;
import com.bee32.plover.orm.entity.EntityAccessor;
import com.bee32.plover.orm.entity.EntityBean;

public abstract class EntityDto<E extends EntityBean<K>, K extends Serializable>
        extends DataTransferObject<E> {

    private static final long serialVersionUID = 1L;

    protected K id;
    protected Integer version;

    public EntityDto() {
        super();
    }

    public EntityDto(E source, int selection) {
        super(source, selection);
    }

    public EntityDto(E source) {
        super(source);
    }

    public EntityDto(int selection) {
        super(selection);
    }

    public K getId() {
        return id;
    }

    public void setId(K id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public EntityDto<E, K> clearId() {
        this.id = null;
        this.version = null;
        return this;
    }

    @Override
    public void marshal(E source) {
        id = source.getId();
        version = source.getVersion();
        _marshal(source);
    }

    @Override
    public void unmarshalTo(E target) {
        EntityAccessor.setId(target, id);

        if (version != null)
            EntityAccessor.setVersion(target, version);

        _unmarshalTo(target);
    }

    @Override
    protected abstract void _marshal(E source);

    @Override
    protected abstract void _unmarshalTo(E target);

}
