package com.bee32.plover.orm.util;

import java.io.Serializable;

import javax.free.IVariantLookupMap;
import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.arch.util.DataTransferObject;
import com.bee32.plover.arch.util.PropertyAccessor;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityAccessor;
import com.bee32.plover.orm.entity.EntityUtil;
import com.bee32.plover.orm.entity.IEntity;

public abstract class EntityDto<E extends Entity<K>, K extends Serializable>
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
        super._setSourceType(entityType);
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
    protected IUnmarshalContext _getDefaultContext() {
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

    /**
     * <table>
     * <tr>
     * <th>DTO</th>
     * <th>DTO.filled</th>
     * <th>DTO.id</th>
     * <th>Result</th>
     * </tr>
     * <tr>
     * <td>*</td>
     * <td>not-filled</td>
     * <td>null</td>
     * <td>ENTITY</td>
     * </tr>
     * <tr>
     * <td>*</td>
     * <td>not-filled</td>
     * <td>not-null</td>
     * <td>ENTITY = get(dto.id)</td>
     * </tr>
     * <tr>
     * <td>null</td>
     * <td>*</td>
     * <td>*</td>
     * <td>ENTITY</td>
     * </tr>
     * <tr>
     * <td>*</td>
     * <td>filled</td>
     * <td>null</td>
     * <td>ENTITY = unmarshal()</td>
     * </tr>
     * <tr>
     * <td>*</td>
     * <td>filled</td>
     * <td>not-null</td>
     * <td>unmarshalTo(ENTITY = get(dto.id))</td>
     * </tr>
     * </table>
     */
    protected static <Ei extends Entity<Ki>, Ki extends Serializable> //
    /**/Ei unmarshal(IUnmarshalContext context, Class<Ei> entityType, //
            Ei oldEntity, EntityDto<Ei, Ki> dto) {

        if (dto == null)
            return oldEntity;

        Ki id = dto.getId();

        if (dto._isFilled()) {
            if (id == null)
                return dto.unmarshal(context); // unmarshalContext
            else {
                Ei existing = context.loadEntity(entityType, id);
                dto.unmarshalTo(context, existing);
                return existing;
            }
        } else {
            if (id == null)
                // DTO(null)
                return oldEntity;
            else
                return context.loadEntity(entityType, id);
        }
    }

    /**
     * <table>
     * <tr>
     * <th>DTO</th>
     * <th>DTO.filled</th>
     * <th>DTO.id</th>
     * <th>Result</th>
     * </tr>
     * <tr>
     * <td>*</td>
     * <td>not-filled</td>
     * <td>null</td>
     * <td>ENTITY</td>
     * </tr>
     * <tr>
     * <td>*</td>
     * <td>not-filled</td>
     * <td>not-null</td>
     * <td>ENTITY = get(dto.id)</td>
     * </tr>
     * <tr>
     * <td>null</td>
     * <td>*</td>
     * <td>*</td>
     * <td>ENTITY</td>
     * </tr>
     * <tr>
     * <td>*</td>
     * <td>filled</td>
     * <td>null</td>
     * <td>ENTITY = unmarshal()</td>
     * </tr>
     * <tr>
     * <td>*</td>
     * <td>filled</td>
     * <td>not-null</td>
     * <td>unmarshalTo(ENTITY = get(dto.id))</td>
     * </tr>
     * </table>
     */
    protected static <E extends Entity<?>, Ei extends Entity<Ki>, Ki extends Serializable> //
    /**/void unmarshal(IUnmarshalContext context, E target, //
            PropertyAccessor<E, Ei> property, EntityDto<Ei, Ki> newDto) {

        Class<Ei> propertyType = property.getType();

        Ei oldProperty = property.get(target);
        Ei newProperty = unmarshal(context, propertyType, oldProperty, newDto);

        if (newProperty != oldProperty)
            property.set(target, newProperty);
    }

    protected static class WithContext<E extends Entity<?>> {

        IUnmarshalContext context;
        E target;

        public WithContext(IUnmarshalContext context, E target) {
            this.context = context;
            this.target = target;
        }

        /**
         * <table>
         * <tr>
         * <th>DTO</th>
         * <th>DTO.filled</th>
         * <th>DTO.id</th>
         * <th>Result</th>
         * </tr>
         * <tr>
         * <td>*</td>
         * <td>not-filled</td>
         * <td>null</td>
         * <td>ENTITY</td>
         * </tr>
         * <tr>
         * <td>*</td>
         * <td>not-filled</td>
         * <td>not-null</td>
         * <td>ENTITY = get(dto.id)</td>
         * </tr>
         * <tr>
         * <td>null</td>
         * <td>*</td>
         * <td>*</td>
         * <td>ENTITY</td>
         * </tr>
         * <tr>
         * <td>*</td>
         * <td>filled</td>
         * <td>null</td>
         * <td>ENTITY = unmarshal()</td>
         * </tr>
         * <tr>
         * <td>*</td>
         * <td>filled</td>
         * <td>not-null</td>
         * <td>unmarshalTo(ENTITY = get(dto.id))</td>
         * </tr>
         * </table>
         */
        public <Ei extends Entity<Ki>, Ki extends Serializable> //
        /**/WithContext<E> unmarshal(PropertyAccessor<E, Ei> property, EntityDto<Ei, Ki> dto) {

            EntityDto.unmarshal(context, target, property, dto);

            return this;
        }

    }

    public <Et extends Entity<?>> WithContext<Et> with(IUnmarshalContext context, Et target) {
        return new WithContext<Et>(context, target);
    }

}
