package com.bee32.plover.orm.util;

import java.io.Serializable;

import javax.free.IVariantLookupMap;
import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.arch.util.DataTransferObject;
import com.bee32.plover.arch.util.PropertyAccessor;
import com.bee32.plover.orm.entity.EntityAccessor;
import com.bee32.plover.orm.entity.EntityBean;
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
    protected IUnmarshalContext defaultContext() {
        return IUnmarshalContext.NULL;
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

    protected static <D extends EntityDto<?, K>, K extends Serializable> K id(D dto) {
        if (dto == null)
            return null;
        else
            return dto.getId();
    }

    protected static <E extends EntityBean<K>, K extends Serializable> //
    /**/E unmarshal(IUnmarshalContext context, Class<E> entityType, E oldEntity, //
            K newId, EntityDto<E, K> newDto) {

        if (newId != null)
            return context.loadEntity(entityType, newId);

        if (newDto == null)
            return null;

        K existingId = newDto.getId();
        if (existingId == null)
            return newDto.unmarshal(); // unmarshalContext

        E existing = context.loadEntity(entityType, existingId);
        newDto.unmarshalTo(existing);
        return existing;
    }

    protected static <E extends EntityBean<?>, Ei extends EntityBean<Ki>, Ki extends Serializable> //
    /**/void unmarshalProperty(IUnmarshalContext context, E target, PropertyAccessor<E, Ei> property, Ki newId,
            EntityDto<Ei, Ki> newDto) {

        Class<Ei> propertyType = property.getType();

        Ei oldProperty = property.get(target);
        Ei newProperty = unmarshal(context, propertyType, oldProperty, newId, newDto);

        if (newProperty != oldProperty)
            property.set(target, newProperty);
    }

    protected static class WithContext<E extends EntityBean<?>> {

        IUnmarshalContext context;
        E target;

        public WithContext(IUnmarshalContext context, E target) {
            this.context = context;
            this.target = target;
        }

        public <Ei extends EntityBean<Ki>, Ki extends Serializable> //
        /**/WithContext<E> unmarshal(PropertyAccessor<E, Ei> property, //
                Ki newId, EntityDto<Ei, Ki> newDto) {

            EntityDto.unmarshalProperty(context, target, property, newId, newDto);

            return this;
        }

    }

    public <Et extends EntityBean<?>> WithContext<Et> with(IUnmarshalContext context, Et target) {
        return new WithContext<Et>(context, target);
    }

}
