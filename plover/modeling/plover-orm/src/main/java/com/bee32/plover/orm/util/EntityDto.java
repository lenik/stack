package com.bee32.plover.orm.util;

import java.io.Serializable;

import javax.free.IVariantLookupMap;
import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.arch.util.DataTransferObject;
import com.bee32.plover.orm.entity.EntityAccessor;
import com.bee32.plover.orm.entity.EntityBean;

public abstract class EntityDto<E extends EntityBean<K>, K extends Serializable>
        extends DataTransferObject<E> {

    private static final long serialVersionUID = 1L;

    protected K id;
    protected Integer version;

    // protected String name;

    public EntityDto() {
        super();
    }

    public EntityDto(int selection) {
        super(selection);
    }

    public EntityDto(E source) {
        super(source);
    }

    public EntityDto(E source, int selection) {
        super(source, selection);
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
    protected final void __marshal(E source) {
        id = source.getId();
        version = source.getVersion();

        _marshal(source);
    }

    @Override
    public void unmarshalTo(E target) {
        if (id != null)
            EntityAccessor.setId(target, id);

        if (version != null)
            EntityAccessor.setVersion(target, version);

        _unmarshalTo(target);
    }

    @Override
    protected abstract void _marshal(E source);

    @Override
    protected abstract void _unmarshalTo(E target);

    @Override
    public void parse(IVariantLookupMap<String> map)
            throws ParseException, TypeConvertException {
        super.parse(map);

        String _id = map.getString("id");
        if (_id == null)
            id = null;
        else
            id = parseId(_id);

        String _version = map.getString("version");
        if (_version == null)
            version = null;
        else
            try {
                version = Integer.parseInt(_version);
            } catch (NumberFormatException e) {
                throw new ParseException("Version isn't an integer: " + _version);
            }
    }

    /**
     * TODO Get the key type.
     *
     * @param s
     *            Non-<code>null</code> string contains the id.
     */
    protected K parseId(String s) {
        return null;
    }

}
