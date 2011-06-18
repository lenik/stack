package com.bee32.plover.orm.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.free.IllegalUsageException;
import javax.free.NotImplementedException;
import javax.free.Nullables;
import javax.free.ParseException;
import javax.free.TypeConvertException;

import org.springframework.context.ApplicationContext;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.arch.util.dto.BaseDto;
import com.bee32.plover.arch.util.dto.MarshalType;
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
        extends BaseDto<E, IEntityMarshalContext>
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
        Object context = getSession().getContext();
        IEntityMarshalContext emContext = (IEntityMarshalContext) context;
        _E entity = emContext.loadEntity(entityType, id);
        return entity;
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
     *            Formatted id string, a <code>null</code> or empty string means null.
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
     * Reference by id, the id is parsed from the idString.
     *
     * @param idString
     *            Specify <code>null</code> for null-ref.
     * @return This DTO object.
     * @see #ref(Serializable)
     */
    public <D extends EntityDto<E, K>> D parseRef(String idString)
            throws ParseException {
        K id = parseId(idString);
        return ref(id);
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
        marshalAs(MarshalType.ID_REF); // ID_VER_REF
        if (entity == null) {
            this.setId(null);
            this.setVersion(null);
        } else {
            this.setId(entity.getId());
            this.setVersion(entity.getVersion());
        }

        @SuppressWarnings("unchecked")
        D self = (D) this;
        return self;
    }

    public <D extends EntityDto<E, K>> D ref(D dto) {
        if (dto == null)
            throw new NullPointerException("dto");

        marshalAs(MarshalType.ID_REF); // ID_VER_REF

        setId(dto.getId());
        setVersion(dto.getVersion());

        @SuppressWarnings("unchecked")
        D self = (D) this;
        return self;
    }

    @Override
    protected E mergeDeref(E given) {
        Class<? extends E> entityType = getEntityType();

        if (isNullRef())
            return null;

        switch (marshalType) {
        case ID_REF:
            if (given != null) {
                // Return the given entity immediately if id matches.
                K givenId = given.getId();
                if (Nullables.equals(id, givenId))
                    return given;
            }

            return loadEntity(entityType, id);

        case ID_VER_REF:
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

            // Instead of loadEntity("null"-id), we allocate a new entity here.
            // The null-key is not supported here.
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

}
