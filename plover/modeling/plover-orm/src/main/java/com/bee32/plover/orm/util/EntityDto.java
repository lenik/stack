package com.bee32.plover.orm.util;

import java.beans.IntrospectionException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.free.IllegalUsageException;
import javax.free.NotImplementedException;
import javax.free.ParseException;
import javax.free.TypeConvertException;

import org.springframework.context.ApplicationContext;

import com.bee32.plover.arch.util.BeanPropertyAccessor;
import com.bee32.plover.arch.util.DataTransferObject;
import com.bee32.plover.arch.util.IMarshalSession;
import com.bee32.plover.arch.util.IPropertyAccessor;
import com.bee32.plover.arch.util.MarshalType;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityAccessor;
import com.bee32.plover.orm.entity.EntityFlags;
import com.bee32.plover.orm.entity.EntityUtil;
import com.bee32.plover.orm.entity.IEntity;
import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.plover.util.FormatStyle;
import com.bee32.plover.util.IMultiFormat;
import com.bee32.plover.util.PrettyPrintStream;

public abstract class EntityDto<E extends Entity<K>, K extends Serializable>
        extends DataTransferObject<E, IEntityMarshalContext>
        implements IMultiFormat {

    private static final long serialVersionUID = 1L;

    protected Class<? extends K> keyType;

    protected K id;
    Integer version;

    Date createdDate;
    Date lastModified;
    boolean createdDateSet;
    boolean lastModifiedSet;

    EntityFlags entityFlags;

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

    @Override
    public void initSourceType(Class<? extends E> entityType) {
        super.initSourceType(entityType);
        keyType = EntityUtil.getKeyType(entityType);
    }

    protected Class<? extends E> getEntityType() {
        return sourceType;
    }

    protected Class<? extends K> getKeyType() {
        return keyType;
    }

    protected <_E extends Entity<_K>, _K extends Serializable> _E loadEntity(Class<_E> entityType, _K id) {
        return getSession().getContext().loadEntity(entityType, id);
    }

    @Override
    protected IEntityMarshalContext getDefaultMarshalContext() {
        ApplicationContext applicationContext = ThreadHttpContext.getApplicationContext();
        DefaultMarshalContext marshalContext = applicationContext.getBean(DefaultMarshalContext.class);
        return marshalContext;
    }

    /**
     * <pre>
     * BASE LAYER: Common Properties
     * -----------------------------------------------------------------------
     *      ID
     *      Version,
     *      Created-Date
     *      Last-Modified
     *      Entity-Flags
     *
     * And toString() implementation.
     * </pre>
     */

    /**
     * Get ID.
     *
     * @return <code>null</code> If id isn't set. Thus should be skipped.
     */
    public K getId() {
        return id;
    }

    /**
     * Set ID.
     *
     * @param id
     *            Set to <code>null</code> to skip the id property.
     */
    public void setId(K id) {
        this.id = id;
    }

    /**
     * Get corresponding version.
     *
     * The version of the entity to which this DTO is marshal/unmarshal against.
     *
     * @return <code>null</code> if version isn't used.
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * Set the corresponding version.
     *
     * The version of the entity to which this DTO is marshal/unmarshal against.
     *
     * @param version
     *            Set to <code>null</code> to skip version.
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * Clear both ID and version field.
     *
     * @return This object.
     */
    public <$ extends EntityDto<E, K>> $ clearId() {
        this.id = null;
        this.version = null;

        @SuppressWarnings("unchecked")
        $ _this = ($) this;

        return _this;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
        this.createdDateSet = true;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
        this.lastModifiedSet = true;
    }

    public EntityFlags getEntityFlags() {
        if (entityFlags == null) {
            synchronized (this) {
                if (entityFlags == null) {
                    entityFlags = new EntityFlags();
                }
            }
        }
        return entityFlags;
    }

    public void setEntityFlags(int entityFlags) {
        this.entityFlags = new EntityFlags(entityFlags);
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

    /**
     * <pre>
     * BASE LAYER: Constructor helpers
     * -----------------------------------------------------------------------
     *      * id-related helpers
     * -----------------------------------------------------------------------
     * </pre>
     */

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
     * <pre>
     * LAYER 1 - Entity Commons
     * -----------------------------------------------------------------------
     *      This layer deal with the common entity properties.
     * -----------------------------------------------------------------------
     *      __marshal
     *      __unmarshalTo
     *      __parse
     *      __export
     * </pre>
     */

    /**
     * Marshal the common entity fields from the source entity.
     */
    @Override
    protected void __marshal(E source) {
        id = source.getId();
        version = source.getVersion();
        createdDate = source.getCreatedDate();
        lastModified = source.getLastModified();

        setEntityFlags(EntityAccessor.getFlags(source).bits);
    }

    /**
     * Unmarshal the common entity fields into the target entity.
     */
    @Override
    protected void __unmarshalTo(E target) {
        if (id != null)
            EntityAccessor.setId(target, id);

        if (version != null)
            EntityAccessor.setVersion(target, version);

        if (createdDateSet)
            EntityAccessor.setCreatedDate(target, createdDate);

        if (lastModifiedSet)
            EntityAccessor.setLastModified(target, lastModified);

        if (entityFlags != null)
            EntityAccessor.getFlags(target).set(entityFlags.bits);
    }

    @Override
    protected void __parse(TextMap map)
            throws ParseException, TypeConvertException {
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

        Date createdDate = map.getDate("createdDate");
        if (createdDate != null)
            setCreatedDate(createdDate);

        Date lastModified = map.getDate("lastModified");
        if (lastModified != null)
            setLastModified(lastModified);

        Integer _entityFlags = map.getNInt("entityFlags");
        if (_entityFlags != null)
            setEntityFlags(_entityFlags);
    }

    @Override
    protected void __export(Map<String, Object> map) {
        if (id != null)
            map.put("id", id);

        if (version != null)
            map.put("version", version);

        if (createdDateSet)
            map.put("createdDate", createdDate);

        if (lastModifiedSet)
            map.put("lastModified", lastModified);

        if (entityFlags != null)
            map.put("entityFlags", entityFlags);
    }

    /**
     * <pre>
     * LAYER 2: REF-Property
     * -----------------------------------------------------------------------
     *      Change this DTO into a reference.
     * -----------------------------------------------------------------------
     * </pre>
     */

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
        marshalAs(MarshalType.ID_REF);

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
            marshalAs(MarshalType.NULL);
        } else {
            this.id = entity.getId();
            this.version = entity.getVersion();
            marshalAs(MarshalType.ID_REF); // ID_VER_REF
        }

        @SuppressWarnings("unchecked")
        D self = (D) this;
        return self;
    }

    public <D extends EntityDto<E, K>> D ref(D dto) {
        if (dto == null)
            throw new NullPointerException("dto");

        this.id = dto.getId();
        this.version = dto.getVersion();
        marshalAs(MarshalType.ID_REF); // ID_VER_REF

        @SuppressWarnings("unchecked")
        D self = (D) this;
        return self;
    }

    @Override
    protected E mergeDeref(E given) {
        Class<? extends E> entityType = getEntityType();

        switch (getMarshalType()) {
        case NULL:
            return null;

        case ID_REF:
            if (id == null)
                throw new IllegalUsageException("ID-REF but id isn't set.");

            if (given != null) {
                // Return the given entity immediately if id matches.
                K givenId = given.getId();
                if (givenId != null && givenId.equals(id))
                    return given;
            }

            return loadEntity(entityType, id);

        case ID_VER_REF:
            if (id == null)
                throw new IllegalUsageException("ID-VER-REF but id isn't set.");
            if (version == null)
                throw new IllegalUsageException("ID-VER-REF but version isn't set.");

            E entity = loadEntity(entityType, id);
            if (!version.equals(entity.getVersion()))
                throw new IllegalStateException("ID-VER-REF but version of entity has been changed.");

            return entity;

        case NAME_REF:
        case OTHER_REF:
        default:
            throw new NotImplementedException("REF by name or other property isn't supportet.");

        case SELECTION:
            if (given != null) // ignore thisDto.id
                return given;

            if (id == null) {
                E newEntity;
                try {
                    newEntity = entityType.newInstance();
                } catch (ReflectiveOperationException e) {
                    throw new IllegalUsageException("Failed to instantiate source bean " + sourceType.getName(), e);
                }
                return newEntity;
            } else {
                E existing = loadEntity(entityType, id);
                return existing;
            }
        } // switch
    }

    /**
     * <pre>
     * LAYER 3: Unmarshal Collection
     * -----------------------------------------------------------------------
     *      unmarshalCollection
     *      unmarshalList
     *      unmarshalSet
     *      TODO unmarshalMap?
     * -----------------------------------------------------------------------
     * </pre>
     */

    /**
     * Experimental.
     */
    static boolean normalizeNulls = false;

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
    /*    */Coll mergeCollection(IMarshalSession<IEntityMarshalContext> session, Coll collection,
            Iterable<? extends D> dtoList) {

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
            if (dto == null) // DTO == null means ignore.
                continue;

            E entity;

            switch (dto.getMarshalType()) {
            case NULL:
                // entity = null;
                nullCount++;
                break;

            case ID_REF:
            case ID_VER_REF: // TODO VER FIX.
                E ref = keyMap.get(dto.id);
                if (ref != null) {

                    removeList.remove(ref);

                } else {
                    if (session == null)
                        entity = dto.unmarshal();
                    else
                        entity = dto.unmarshal(session);

                    addList.add(entity);
                }
                break;

            case NAME_REF:
            case OTHER_REF:
            default:
                throw new NotImplementedException();

            case SELECTION:
                if (dto.id == null) {
                    if (session == null)
                        entity = dto.unmarshal();
                    else
                        entity = dto.unmarshal(session);

                    E reuse = contentMap.get(entity);
                    if (reuse != null) {
                        // We are not going to partial-modify elements in collection-unmarshalling.
                        // XXX dto.unmarshalTo(context, reuse);
                        // XXX reuse.populate(entity); ??
                        entity = reuse;
                        removeList.remove(reuse);
                    } else {
                        addList.add(entity);
                    }
                } else {
                    E previous = keyMap.get(dto.id);
                    if (previous != null) {
                        if (session == null)
                            dto.unmarshalTo(previous);
                        else
                            dto.unmarshalTo(session, previous);

                        removeList.remove(previous);

                    } else {
                        if (session == null)
                            entity = dto.unmarshal();
                        else
                            entity = dto.unmarshal(session);

                        addList.add(entity);
                    }
                }
            }
        } // for dtoList

        if (removeList != null)
            collection.removeAll(removeList);

        if (addList != null)
            collection.addAll(addList);

        if (normalizeNulls && nullCount != 0) {
            while (collection.contains(null))
                collection.remove(null);
            for (int i = 0; i < nullCount; i++)
                collection.add(null);
        }

        return collection;
    }

    static <Coll extends Collection<E>, D extends EntityDto<E, K>, E extends Entity<K>, K extends Serializable> //
    /*    */Coll mergeCollection(Coll collection, Iterable<? extends D> dtoList) {
        return mergeCollection((IMarshalSession<IEntityMarshalContext>) null, collection, dtoList);
    }

    public static <D extends EntityDto<E, K>, E extends Entity<K>, K extends Serializable> //
    /*    */List<E> mergeList(IMarshalSession<IEntityMarshalContext> session, List<E> list, Iterable<? extends D> dtoList) {
        if (list == null)
            list = new ArrayList<E>();
        return mergeCollection(session, list, dtoList);
    }

    public static <D extends EntityDto<E, K>, E extends Entity<K>, K extends Serializable> //
    /*    */List<E> mergeList(List<E> list, Iterable<? extends D> dtoList) {
        if (list == null)
            list = new ArrayList<E>();
        return mergeCollection((IMarshalSession<IEntityMarshalContext>) null, list, dtoList);
    }

    public static <D extends EntityDto<E, K>, E extends Entity<K>, K extends Serializable> //
    /*    */Set<E> mergeSet(IMarshalSession<IEntityMarshalContext> session, Set<E> set, Iterable<? extends D> dtoList) {
        if (set == null)
            set = new HashSet<E>();
        return mergeCollection(session, set, dtoList);
    }

    public static <D extends EntityDto<E, K>, E extends Entity<K>, K extends Serializable> //
    /*    */Set<E> mergeSet(Set<E> set, Iterable<? extends D> dtoList) {
        if (set == null)
            set = new HashSet<E>();
        return mergeCollection((IMarshalSession<IEntityMarshalContext>) null, set, dtoList);
    }

    /**
     * <pre>
     * LAYER 4: Merge collection properties
     * -----------------------------------------------------------------------
     *      mergeCollection
     *      mergeList
     *      mergeSet
     *      TODO mergeMap?
     * -----------------------------------------------------------------------
     * </pre>
     */

    public static <E extends Entity<?>, _d extends EntityDto<_e, _k>, _e extends Entity<_k>, _k extends Serializable> //
    /*    */void mergeList(IMarshalSession<IEntityMarshalContext> session, E target,
            IPropertyAccessor<E, List<_e>> property, Iterable<? extends _d> dtoList) {

        List<_e> list = property.get(target);

        if (list == null)
            list = new ArrayList<_e>();

        if (session == null)
            list = EntityDto.mergeList(list, dtoList);
        else
            list = EntityDto.mergeList(session, list, dtoList);

        property.set(target, list);
    }

    public static <_E extends Entity<?>, _d extends EntityDto<_e, _k>, _e extends Entity<_k>, _k extends Serializable> //
    /*    */void mergeList(_E target, IPropertyAccessor<_E, List<_e>> property, Iterable<? extends _d> dtoList) {
        mergeList(null, target, property, dtoList);
    }

    public static <E extends Entity<?>, _d extends EntityDto<_e, _k>, _e extends Entity<_k>, _k extends Serializable> //
    /*    */void mergeList(IMarshalSession<IEntityMarshalContext> session, E target, String propertyName,
            Iterable<? extends _d> dtoList) {

        Class<E> targetType = (Class<E>) target.getClass();

        IPropertyAccessor<E, List<_e>> property;
        try {
            property = BeanPropertyAccessor.access(targetType, propertyName);
        } catch (IntrospectionException e) {
            // XXX Error message?
            throw new IllegalUsageException(e.getMessage(), e);
        }

        mergeList(session, target, property, dtoList);
    }

    public static <E extends Entity<?>, _d extends EntityDto<_e, _k>, _e extends Entity<_k>, _k extends Serializable> //
    /*    */void mergeList(E target, String propertyName, Iterable<? extends _d> dtoList) {
        mergeList(null, target, propertyName, dtoList);
    }

    public static <E extends Entity<?>, _d extends EntityDto<_e, _k>, _e extends Entity<_k>, _k extends Serializable> //
    /*    */void mergeSet(IMarshalSession<IEntityMarshalContext> session, E target,
            IPropertyAccessor<E, Set<_e>> property, Iterable<? extends _d> dtoList) {

        Set<_e> set = property.get(target);

        if (set == null)
            set = new HashSet<_e>();

        if (session == null)
            set = EntityDto.mergeSet(set, dtoList);
        else
            set = EntityDto.mergeSet(session, set, dtoList);

        property.set(target, set);
    }

    public static <E extends Entity<?>, _d extends EntityDto<_e, _k>, _e extends Entity<_k>, _k extends Serializable> //
    /*    */void mergeSet(E target, IPropertyAccessor<E, Set<_e>> property, Iterable<? extends _d> dtoList) {
        mergeSet(null, target, property, dtoList);
    }

    public static <E extends Entity<?>, _d extends EntityDto<_e, _k>, _e extends Entity<_k>, _k extends Serializable> //
    /*    */void mergeSet(IMarshalSession<IEntityMarshalContext> session, E target, String propertyName,
            Iterable<? extends _d> dtoList) {

        Class<E> targetType = (Class<E>) target.getClass();

        IPropertyAccessor<E, Set<_e>> property;
        try {
            property = BeanPropertyAccessor.access(targetType, propertyName);
        } catch (IntrospectionException e) {
            // XXX Error message?
            throw new IllegalUsageException(e.getMessage(), e);
        }

        mergeSet(session, target, property, dtoList);
    }

    public static <E extends Entity<?>, _d extends EntityDto<_e, _k>, _e extends Entity<_k>, _k extends Serializable> //
    /*    */void mergeSet(E target, String propertyName, Iterable<? extends _d> dtoList) {
        mergeSet(null, target, propertyName, dtoList);
    }

}
