package com.bee32.plover.orm.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.free.IllegalUsageException;
import javax.free.ParseException;
import javax.free.TypeConvertException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataAccessException;

import com.bee32.plover.arch.util.DataTransferObject;
import com.bee32.plover.arch.util.PropertyAccessor;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityAccessor;
import com.bee32.plover.orm.entity.EntityUtil;
import com.bee32.plover.orm.entity.IEntity;
import com.bee32.plover.util.FormatStyle;
import com.bee32.plover.util.IMultiFormat;
import com.bee32.plover.util.PrettyPrintStream;

public abstract class EntityDto<E extends Entity<K>, K extends Serializable>
        extends DataTransferObject<E, IUnmarshalContext>
        implements IMultiFormat {

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

    public EntityDto(int selection, E source) {
        super(selection, source);
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

    /**
     * Set this DTO as a "reference pointer", and clear the filled state.
     *
     * <p>
     * All properties except <code>id</code> and <code>version</code> should be ignored by user.
     *
     * @param id
     *            The id to the target entity;
     * @return This DTO object.
     * @see #ref(Entity)
     */
    public <D extends EntityDto<E, K>> D ref(K id) {
        this.id = id;
        this.version = null;
        this.filled = false;

        @SuppressWarnings("unchecked")
        D self = (D) this;
        return self;
    }

    /**
     * Set this DTO as a "reference pointer", and clear the filled state.
     *
     * <p>
     * All properties except <code>id</code> and <code>version</code> should be ignored by user.
     *
     * @param entity
     *            The target entity this DTO would reference to.
     * @return This DTO.
     */
    public <D extends EntityDto<E, K>> D ref(E entity) {
        if (entity == null) {
            this.id = null;
            this.version = null;
        } else {
            this.id = entity.getId();
            this.version = entity.getVersion();
        }
        this.filled = false;

        @SuppressWarnings("unchecked")
        D self = (D) this;
        return self;
    }

    public <D extends EntityDto<E, K>> D ref(D dto) {
        if (dto == null)
            throw new NullPointerException("dto");

        this.id = dto.getId();
        this.version = dto.getVersion();
        this.filled = false;

        @SuppressWarnings("unchecked")
        D self = (D) this;
        return self;
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

    /**
     * Unmarshal a property DTO into the corresponding property of an entity in a smart way.
     * <p>
     * The contents in the given property DTO may be fully unmarshalled to a new created entity
     * bean, or unmarshalled to be reference to the existing entity, or between. The behaviour is
     * determined on the two states of the DTO object: the <i>filled</i> state, and the <i>id</i>
     * state.
     * <p>
     *
     * Transfer table:
     * <table border="1">
     * <tr>
     * <th>DTO.filled</th>
     * <th>DTO.id</th>
     * <th>Meaning</th>
     * <th>Result</th>
     * </tr>
     * <tr>
     * <td align="center">not-filled</td>
     * <td align="center"><code>null</code></td>
     * <td align="center">Set to null</td>
     * <td align="left"><code>null</code></td>
     * </tr>
     * <tr>
     * <td align="center">not-filled</td>
     * <td align="center">id</td>
     * <td align="center">Reference</td>
     * <td align="left">ENTITY = reference&lt;dto.id&gt;</td>
     * </tr>
     * <tr>
     * <td align="center">filled</td>
     * <td align="center"><code>null</code></td>
     * <td align="center">Full Reconstruction</td>
     * <td align="left">ENTITY = unmarshal()</td>
     * </tr>
     * <tr>
     * <td align="center">filled</td>
     * <td align="center">id</td>
     * <td align="center">Partial Modification</td>
     * <td align="left">unmarshalTo(ENTITY = reference&lt;dto.id&gt;)</td>
     * </tr>
     * </table>
     *
     * The DTO is only filled if:
     * <ul>
     * <li>It has been {@link DataTransferObject#marshal(Object) marhsalled} from an entity bean.
     * <li>It has {@link DataTransferObject#parse(Map) parsed from a Map}, or
     * {@link DataTransferObject#parse(HttpServletRequest) from an HttpServletRequest}.</li>
     *
     * You can always clear the filled state by reference the DTO to a specific id, by calling the
     * {@link EntityDto#ref(Entity) ref} method.
     *
     * @param property
     *            The property accessor which is used to get the old property value, and set the
     *            property to a new value.
     * @param dto
     *            The data transfer object which will be unmarshalled into the property of the
     *            context target bean, using the specified property accessor.
     * @return This {@link WithContext} object, for chaining method calls purpose.
     * @throws DataAccessException
     *             If data access exception happened with calls into the {@link IUnmarshalContext}.
     */
    @Override
    public final E unmarshal(IUnmarshalContext context) {
        Class<? extends E> entityType = getEntityType();
        if (filled) {
            if (id == null) {
                E newEntity;
                try {
                    newEntity = entityType.newInstance();
                } catch (ReflectiveOperationException e) {
                    throw new IllegalUsageException("Failed to instantiate source bean " + sourceType.getName(), e);
                }
                unmarshalTo(context, newEntity);
                return newEntity;
            } else {
                E existing = context.loadEntity(entityType, id);
                unmarshalTo(context, existing);
                return existing;
            }
        } else {
            if (id == null)
                return null;
            else
                return context.loadEntity(entityType, id);
        }
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
    protected void __parse(TextMap map)
            throws ParseException, TypeConvertException {
        super.__parse(map);

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
     * @throws ParseException
     * @see EntityUtil#parseId(Class, String)
     */
    protected K parseId(String idString)
            throws ParseException {
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

    protected static <D extends EntityDto<?, K>, K extends Serializable> List<K> id(Iterable<? extends D> dtos) {
        List<K> idList = new ArrayList<K>();

        if (dtos != null)
            for (D dto : dtos)
                idList.add(id(dto));

        return idList;
    }

    protected static <E extends IEntity<K>, K extends Serializable> List<K> idOfEntities(Iterable<? extends E> entities) {
        List<K> idList = new ArrayList<K>();

        if (entities != null)
            for (E entity : entities)
                idList.add(id(entity));

        return idList;
    }

    /**
     * Unmarshal a property DTO into the corresponding property of an entity in a smart way.
     * <p>
     * The contents in the given property DTO may be fully unmarshalled to a new created entity
     * bean, or unmarshalled to be reference to the existing entity, or between. The behaviour is
     * determined on the two states of the DTO object: the <i>filled</i> state, and the <i>id</i>
     * state.
     * <p>
     *
     * Transfer table:
     * <table border="1">
     * <tr>
     * <th>DTO</th>
     * <th>DTO.filled</th>
     * <th>DTO.id</th>
     * <th>Meaning</th>
     * <th>Result</th>
     * </tr>
     * <tr>
     * <td align="center"><code>null</code></td>
     * <td align="center">(n/a)</td>
     * <td align="center">(n/a)</td>
     * <td align="center">Skip, no change</td>
     * <td align="left">ENTITY</td>
     * </tr>
     * <tr>
     * <td align="center">*</td>
     * <td align="center">not-filled</td>
     * <td align="center"><code>null</code></td>
     * <td align="center">Set to null</td>
     * <td align="left"><code>null</code></td>
     * </tr>
     * <tr>
     * <td align="center">*</td>
     * <td align="center">not-filled</td>
     * <td align="center">id</td>
     * <td align="center">Reference</td>
     * <td align="left">ENTITY = reference&lt;dto.id&gt;</td>
     * </tr>
     * <tr>
     * <td align="center">*</td>
     * <td align="center">filled</td>
     * <td align="center"><code>null</code></td>
     * <td align="center">Full Reconstruction</td>
     * <td align="left">ENTITY = unmarshal()</td>
     * </tr>
     * <tr>
     * <td align="center">*</td>
     * <td align="center">filled</td>
     * <td align="center">id</td>
     * <td align="center">Partial Modification</td>
     * <td align="left">unmarshalTo(ENTITY = reference&lt;dto.id&gt;)</td>
     * </tr>
     * </table>
     *
     * The DTO is only filled if:
     * <ul>
     * <li>It has been {@link DataTransferObject#marshal(Object) marhsalled} from an entity bean.
     * <li>It has {@link DataTransferObject#parse(Map) parsed from a Map}, or
     * {@link DataTransferObject#parse(HttpServletRequest) from an HttpServletRequest}.</li>
     *
     * You can always clear the filled state by reference the DTO to a specific id, by calling the
     * {@link EntityDto#ref(Entity) ref} method.
     *
     * @param property
     *            The property accessor which is used to get the old property value, and set the
     *            property to a new value.
     * @param dto
     *            The data transfer object which will be unmarshalled into the property of the
     *            context target bean, using the specified property accessor.
     * @return This {@link WithContext} object, for chaining method calls purpose.
     * @throws DataAccessException
     *             If data access exception happened with calls into the {@link IUnmarshalContext}.
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
                Ei existing = oldEntity; // context.loadEntity(entityType, id);
                dto.unmarshalTo(context, existing);
                return existing;
            }
        } else {
            if (id == null)
                return null;
            else
                return context.loadEntity(entityType, id);
        }
    }

    /**
     * Unmarshal a property DTO into the corresponding property of an entity in a smart way.
     * <p>
     * The contents in the given property DTO may be fully unmarshalled to a new created entity
     * bean, or unmarshalled to be reference to the existing entity, or between. The behaviour is
     * determined on the two states of the DTO object: the <i>filled</i> state, and the <i>id</i>
     * state.
     * <p>
     *
     * Transfer table:
     * <table border="1">
     * <tr>
     * <th>DTO</th>
     * <th>DTO.filled</th>
     * <th>DTO.id</th>
     * <th>Meaning</th>
     * <th>Result</th>
     * </tr>
     * <tr>
     * <td align="center"><code>null</code></td>
     * <td align="center">(n/a)</td>
     * <td align="center">(n/a)</td>
     * <td align="center">Skip, no change</td>
     * <td align="left">ENTITY</td>
     * </tr>
     * <tr>
     * <td align="center">*</td>
     * <td align="center">not-filled</td>
     * <td align="center"><code>null</code></td>
     * <td align="center">Set to null</td>
     * <td align="left"><code>null</code></td>
     * </tr>
     * <tr>
     * <td align="center">*</td>
     * <td align="center">not-filled</td>
     * <td align="center">id</td>
     * <td align="center">Reference</td>
     * <td align="left">ENTITY = reference&lt;dto.id&gt;</td>
     * </tr>
     * <tr>
     * <td align="center">*</td>
     * <td align="center">filled</td>
     * <td align="center"><code>null</code></td>
     * <td align="center">Full Reconstruction</td>
     * <td align="left">ENTITY = unmarshal()</td>
     * </tr>
     * <tr>
     * <td align="center">*</td>
     * <td align="center">filled</td>
     * <td align="center">id</td>
     * <td align="center">Partial Modification</td>
     * <td align="left">unmarshalTo(ENTITY = reference&lt;dto.id&gt;)</td>
     * </tr>
     * </table>
     *
     * The DTO is only filled if:
     * <ul>
     * <li>It has been {@link DataTransferObject#marshal(Object) marhsalled} from an entity bean.
     * <li>It has {@link DataTransferObject#parse(Map) parsed from a Map}, or
     * {@link DataTransferObject#parse(HttpServletRequest) from an HttpServletRequest}.</li>
     *
     * You can always clear the filled state by reference the DTO to a specific id, by calling the
     * {@link EntityDto#ref(Entity) ref} method.
     *
     * @param property
     *            The property accessor which is used to get the old property value, and set the
     *            property to a new value.
     * @param dto
     *            The data transfer object which will be unmarshalled into the property of the
     *            context target bean, using the specified property accessor.
     * @return This {@link WithContext} object, for chaining method calls purpose.
     * @throws DataAccessException
     *             If data access exception happened with calls into the {@link IUnmarshalContext}.
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

    static boolean nullElementsEnabled = false;

    /**
     * It's not append, but assign to the collection.
     *
     * <pre>
     * null                     skip
     * *    not-filled  null    count(null)++
     * *    not-filled  id      if (! contains(id)) add(deref)
     * *    filled      null    add(unmarshal())
     * *    filled      id      if (contains(id)) unmarshalTo(existing)
     *                             else add(unmarshalTo(deref))
     * </pre>
     *
     * deref
     */
    static <Coll extends Collection<E>, D extends EntityDto<E, K>, E extends Entity<K>, K extends Serializable> //
    /*    */Coll unmarshalCollection(IUnmarshalContext context, Coll collection, Iterable<? extends D> dtoList) {

        if (collection == null)
            throw new NullPointerException("collection");

        if (dtoList == null)
            return collection;

        int nullCount = 0;

        List<E> removeList = new ArrayList<E>(collection);
        List<E> addList = new ArrayList<E>();

        Map<K, E> keyMap = new HashMap<K, E>();
        for (E each : collection) {
            K id = each.getId();
            if (id != null)
                keyMap.put(id, each);
        }

        Map<E, E> contentMap = new HashMap<E, E>();
        for (E each : collection)
            if (each != null)
                contentMap.put(each, each);

        for (D dto : dtoList) {
            if (dto == null)
                continue;

            E entity;

            if (dto.filled) {
                if (dto.id == null) {
                    entity = dto.unmarshal(context);

                    E reuse = contentMap.get(entity);
                    if (reuse != null) {
                        // We are not going to partial-modify elements in collection-unmarshalling.
                        // XXX dto.unmarshalTo(context, reuse);
                        // XXX reuse.populate(entity); ??
                        entity = reuse;
                    }

                    addList.add(entity);

                } else {
                    E previous = keyMap.get(dto.id);
                    if (previous != null) {
                        dto.unmarshalTo(context, previous);
                        removeList.remove(previous);
                    } else {
                        entity = dto.unmarshal(context);
                        addList.add(entity);
                    }
                }
            } else {
                if (dto.id == null)
                    nullCount++;
                else {
                    E previous = keyMap.get(dto.id);
                    if (previous != null)
                        removeList.remove(previous);
                    else {
                        entity = dto.unmarshal(context);
                        addList.add(entity);
                    }
                }
            } // filled
        } // for dtoList

        if (removeList != null)
            collection.removeAll(removeList);

        if (addList != null)
            collection.addAll(addList);

        if (nullElementsEnabled && nullCount != 0) {
            while (collection.contains(null))
                collection.remove(null);
            for (int i = 0; i < nullCount; i++)
                collection.add(null);
        }

        return collection;
    }

    static <Coll extends Collection<E>, D extends EntityDto<E, K>, E extends Entity<K>, K extends Serializable> //
    /*    */Coll unmarshalCollection(Coll collection, Iterable<? extends D> dtoList) {
        return unmarshalCollection((IUnmarshalContext) null, collection, dtoList);
    }

    public static <D extends EntityDto<E, K>, E extends Entity<K>, K extends Serializable> //
    /*    */List<E> unmarshalList(IUnmarshalContext context, List<E> list, Iterable<? extends D> dtoList) {
        if (list == null)
            list = new ArrayList<E>();
        return unmarshalCollection(context, list, dtoList);
    }

    public static <D extends EntityDto<E, K>, E extends Entity<K>, K extends Serializable> //
    /*    */List<E> unmarshalList(List<E> list, Iterable<? extends D> dtoList) {
        if (list == null)
            list = new ArrayList<E>();
        return unmarshalCollection((IUnmarshalContext) null, list, dtoList);
    }

    public static <D extends EntityDto<E, K>, E extends Entity<K>, K extends Serializable> //
    /*    */Set<E> unmarshalSet(IUnmarshalContext context, Set<E> set, Iterable<? extends D> dtoList) {
        if (set == null)
            set = new HashSet<E>();
        return unmarshalCollection(context, set, dtoList);
    }

    public static <D extends EntityDto<E, K>, E extends Entity<K>, K extends Serializable> //
    /*    */Set<E> unmarshalSet(Set<E> set, Iterable<? extends D> dtoList) {
        if (set == null)
            set = new HashSet<E>();
        return unmarshalCollection((IUnmarshalContext) null, set, dtoList);
    }

    public <Et extends Entity<?>> WithContext<Et> with(IUnmarshalContext context, Et target) {
        return new WithContext<Et>(context, target);
    }

    protected static class WithContext<E extends Entity<?>> {

        private final IUnmarshalContext context;
        private final E target;

        public WithContext(IUnmarshalContext context, E target) {
            this.context = context;
            this.target = target;
        }

        /**
         * Unmarshal a property DTO into the corresponding property of an entity in a smart way.
         * <p>
         * The contents in the given property DTO may be fully unmarshalled to a new created entity
         * bean, or unmarshalled to be reference to the existing entity, or between. The behaviour
         * is determined on the two states of the DTO object: the <i>filled</i> state, and the
         * <i>id</i> state.
         * <p>
         *
         * Transfer table:
         * <table border="1">
         * <tr>
         * <th>DTO</th>
         * <th>DTO.filled</th>
         * <th>DTO.id</th>
         * <th>Meaning</th>
         * <th>Result</th>
         * </tr>
         * <tr>
         * <td align="center"><code>null</code></td>
         * <td align="center">(n/a)</td>
         * <td align="center">(n/a)</td>
         * <td align="center">Skip, no change</td>
         * <td align="left">ENTITY</td>
         * </tr>
         * <tr>
         * <td align="center">*</td>
         * <td align="center">not-filled</td>
         * <td align="center"><code>null</code></td>
         * <td align="center">Set to null</td>
         * <td align="left"><code>null</code></td>
         * </tr>
         * <tr>
         * <td align="center">*</td>
         * <td align="center">not-filled</td>
         * <td align="center">id</td>
         * <td align="center">Reference</td>
         * <td align="left">ENTITY = reference&lt;dto.id&gt;</td>
         * </tr>
         * <tr>
         * <td align="center">*</td>
         * <td align="center">filled</td>
         * <td align="center"><code>null</code></td>
         * <td align="center">Full Reconstruction</td>
         * <td align="left">ENTITY = unmarshal()</td>
         * </tr>
         * <tr>
         * <td align="center">*</td>
         * <td align="center">filled</td>
         * <td align="center">id</td>
         * <td align="center">Partial Modification</td>
         * <td align="left">unmarshalTo(ENTITY = reference&lt;dto.id&gt;)</td>
         * </tr>
         * </table>
         *
         * The DTO is only filled if:
         * <ul>
         * <li>It has been {@link DataTransferObject#marshal(Object) marhsalled} from an entity
         * bean.
         * <li>It has {@link DataTransferObject#parse(Map) parsed from a Map}, or
         * {@link DataTransferObject#parse(HttpServletRequest) from an HttpServletRequest}.</li>
         *
         * You can always clear the filled state by reference the DTO to a specific id, by calling
         * the {@link EntityDto#ref(Entity) ref} method.
         *
         * @param property
         *            The property accessor which is used to get the old property value, and set the
         *            property to a new value.
         * @param dto
         *            The data transfer object which will be unmarshalled into the property of the
         *            context target bean, using the specified property accessor.
         * @return This {@link WithContext} object, for chaining method calls purpose.
         * @throws DataAccessException
         *             If data access exception happened with calls into the
         *             {@link IUnmarshalContext}.
         */
        public <_E extends Entity<_K>, _K extends Serializable> //
        /**/WithContext<E> unmarshal(PropertyAccessor<E, _E> property, EntityDto<_E, _K> dto) {

            EntityDto.unmarshal(context, target, property, dto);

            return this;
        }

        public <_D extends EntityDto<_E, _K>, _E extends Entity<_K>, _K extends Serializable> //
        /*    */WithContext<E> unmarshalList(PropertyAccessor<E, List<_E>> property, Iterable<? extends _D> dtoList) {

            List<_E> list = property.get(target);

            if (list == null)
                list = new ArrayList<_E>();

            list = EntityDto.unmarshalList(context, list, dtoList);

            property.set(target, list);

            return this;
        }

        public <_D extends EntityDto<_E, _K>, _E extends Entity<_K>, _K extends Serializable> //
        /*    */WithContext<E> unmarshalSet(PropertyAccessor<E, Set<_E>> property, Iterable<? extends _D> dtoList) {

            Set<_E> set = property.get(target);

            if (set == null)
                set = new HashSet<_E>();

            set = EntityDto.unmarshalSet(context, set, dtoList);

            property.set(target, set);

            return this;
        }

    }

    @Override
    public String toString() {
        return toString(FormatStyle.DEFAULT);
    }

    @Override
    public String toString(FormatStyle format) {
        PrettyPrintStream buf = new PrettyPrintStream();
        toString(buf, format);
        return buf.toString();
    }

    @Override
    public void toString(PrettyPrintStream out, FormatStyle format) {
        toString(out, format, null, 0);
    }

    @Override
    public void toString(PrettyPrintStream out, FormatStyle format, Set<Object> occurred, int depth) {
        EntityDtoFormatter formatter = new EntityDtoFormatter(out, occurred);
        formatter.format(this, format, depth);
    }

}
