package com.bee32.plover.orm.util;

import java.io.Serializable;

import javax.free.IVariantLookupMap;
import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.arch.util.DataTransferObject;
import com.bee32.plover.orm.entity.EntityAccessor;
import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.plover.orm.entity.EntityDao;
import com.bee32.plover.orm.entity.EntityUtil;
import com.bee32.plover.orm.entity.IEntity;

public abstract class EntityDto<E extends EntityBean<K>, K extends Serializable>
        extends DataTransferObject<E, IUnmarshalContext> {

    private static final long serialVersionUID = 1L;

    protected K id;
    protected Integer version;

    protected Class<? extends K> keyType;

    public EntityDto() {
        super();
    }

    public EntityDto(E source) {
        super(source);
    }

    public EntityDto(int selection) {
        super(selection);
    }

    public EntityDto(E source, int selection) {
        super(source, selection);
    }

    public void setEntityType(Class<? extends E> entityType) {
        super.setSourceType(entityType);
        keyType = EntityUtil.getKeyType(entityType);
    }

    protected Class<? extends E> getEntityType() {
        return sourceType;
    }

    protected Class<? extends K> getKeyType() {
        return keyType;
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
    protected void __marshal(E source) {
        id = source.getId();
        version = source.getVersion();
    }

    @Override
    protected void __unmarshalTo(IUnmarshalContext context, E target) {
        if (id != null)
            EntityAccessor.setId(target, id);

        if (version != null)
            EntityAccessor.setVersion(target, version);
    }

    static <E extends EntityBean<K>, K extends Serializable, Dao extends EntityDao<E, K>> //
    E unmarshalMember(IUnmarshalContext context, Class<E> entityType, E oldValue, K newId, EntityDto<E, K> newDto) {

        EntityDao<E, K> dao = context.getDao(entityType);

        if (newId != null)
            return dao.get(newId);

        if (newDto == null)
            return null;

        K existingId = newDto.getId();
        if (existingId == null)
            return newDto.unmarshal(); // unmarshalContext

        E existing = dao.load(existingId);
        newDto.unmarshalTo(existing);
        return existing;
    }

    @Override
    public void parse(IVariantLookupMap<String> map)
            throws ParseException, TypeConvertException {
        super.parse(map);

        String _id = map.getString("id");
        if (_id == null || _id.isEmpty()) {
            // 虽然 EntityUtil 中定义了 int/long 类型主键对空字串的解释，
            // 但 DTO 在这里重新将空字串定义为 “不可用”。
            // 如果实体需要支持“空字串"作为主键使用，相应的 DTO 需要推翻此方法。
            id = null;
        } else
            id = parseId(_id);

        String _version = map.getString("version");
        if (_version == null || _version.isEmpty())
            version = null;
        else
            try {
                version = Integer.parseInt(_version);
            } catch (NumberFormatException e) {
                throw new ParseException("Version isn't an integer: " + _version);
            }
    }

    /**
     * Parse the id string. The string should be valid and non-empty.
     *
     * @param idString
     *            Formatted id string.
     * @return Parsed key, maybe <code>null</code>.
     * @see EntityUtil#parseId(Class, String)
     */
    protected K parseId(String idString) {
        return EntityUtil.parseId(getKeyType(), idString);
    }

    protected static <E extends IEntity<K>, K extends Serializable> K id(E entity) {
        if (entity == null)
            return null;
        else
            return entity.getId();
    }

}
