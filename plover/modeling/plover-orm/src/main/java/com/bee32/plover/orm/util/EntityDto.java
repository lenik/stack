package com.bee32.plover.orm.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.free.IllegalUsageException;
import javax.free.NotImplementedException;
import javax.free.Nullables;
import javax.free.ParseException;
import javax.free.TypeConvertException;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateSystemException;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.arch.util.dto.BaseDto;
import com.bee32.plover.arch.util.dto.BaseDto_Skel;
import com.bee32.plover.arch.util.dto.MarshalType;
import com.bee32.plover.model.state.PropetyFlagsMap;
import com.bee32.plover.model.state.StatePropertyFlagsMapMap;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityAccessor;
import com.bee32.plover.orm.entity.EntityBase;
import com.bee32.plover.orm.entity.EntityFlags;
import com.bee32.plover.orm.entity.EntityUtil;
import com.bee32.plover.orm.entity.IIdentity;
import com.bee32.plover.orm.sample.StandardSamples;
import com.bee32.plover.orm.validation.RequiredId;
import com.bee32.plover.restful.resource.IObjectPageDirectory;
import com.bee32.plover.restful.resource.PageDirectory;
import com.bee32.plover.restful.resource.StandardViews;
import com.bee32.plover.rtx.location.Location;
import com.bee32.plover.servlet.rtx.LocationVmap;
import com.bee32.plover.servlet.util.HttpAssembledContext;
import com.bee32.plover.util.FormatStyle;
import com.bee32.plover.util.PrettyPrintStream;

public abstract class EntityDto<E extends Entity<K>, K extends Serializable>
        extends EntityDto_VTU<E, K>
        implements IIdentity<K> {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(EntityDto.class);

    private transient Object _source; // Only used to get parameterized type name.
    LocationVmap defaultPage;

    private int _index;
    private Boolean _checked;

    protected K id;
    private boolean _skipId;
    private Integer version;
    private String altId;
    private int stateInt;
    private Date createdDate;
    private Date lastModified;
    private boolean createdDateDirty;
    private boolean lastModifiedDirty;

    private EntityFlags entityFlags;

    private transient PropetyFlagsMap pfm;

    public EntityDto() {
        super();
        createTransients();
    }

    public EntityDto(int fmask) {
        super(fmask);
        createTransients();
    }

    @Override
    protected void __copy() {
        super.__copy();
        // clearId();
    }

    protected void createTransients() {
    }

    private void readObject(ObjectInputStream in)
            throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        createTransients();
    }

    /**
     * <pre>
     * BASE LAYER: Common Properties
     * -----------------------------------------------------------------------
     *      ID
     *      Version
     *      Created-Date
     *      Last-Modified
     *      Entity-Flags
     *
     * And toString() implementation.
     * </pre>
     */

    @Override
    public boolean isNullRef() {
        return marshalType.isReference() && id == null;
    }

    public <$ extends EntityDto<E, K>> $ idCollapse(K collapseId) {
        if (Nullables.equals(this.id, collapseId))
            ref((K) null);

        @SuppressWarnings("unchecked")
        $ self = ($) this;
        return self;
    }

    /**
     * @see BaseDto_Skel#getPrimaryKey()
     */
    @Override
    public final Serializable getPrimaryKey() {
        return getId();
    }

    /**
     * Clear both ID and version field.
     *
     * @return This object.
     */
    public <$ extends EntityDto<E, K>> $ clearId() {
        this.id = null;
        this.version = null;
        this.createdDate = new Date();
        this.lastModified = this.createdDate;

        @SuppressWarnings("unchecked")
        $ _this = ($) this;
        return _this;
    }

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

    public String getTypeName() {
        if (_source != null)
            return ClassUtil.getParameterizedTypeName(_source);
        else
            return ClassUtil.getTypeName(sourceType);
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
        this._source = source;

        id = source.getId();
        version = source.getVersion();
        _skipId = version == -1;
        altId = source.getAltId();

        setStateInt(source.getStateInt());

        createdDate = source.getCreatedDate();
        lastModified = source.getLastModified();
        setEntityFlags(EntityAccessor.getFlags(source).bits);

        _track(source);
    }

    /**
     * Unmarshal the common entity fields into the target entity.
     */
    @Override
    protected void __unmarshalTo(E target) {
        if (id != null && !_skipId) // XXX EntitySpec should override this.
            EntityAccessor.setId(target, id);

        if (version != null)
            EntityAccessor.setVersion(target, version);

        target.setAltId(altId);
        target.setStateInt(stateInt);

        if (createdDateDirty)
            EntityAccessor.setCreatedDate(target, createdDate);

        if (lastModifiedDirty)
            EntityAccessor.setLastModified(target, lastModified);

        if (entityFlags != null)
            EntityAccessor.getFlags(target).set(entityFlags.bits);

    }

    @Override
    protected void __parse(TextMap map)
            throws ParseException, TypeConvertException {
        String _id = map.getString("id");
        if (_id == null || _id.isEmpty()) {
            // ?????? EntityUtil ???????????? int/long ????????????????????????????????????
            // ??? DTO ???????????????????????????????????? ??????????????????
            // ????????????????????????????????????"?????????????????????????????? DTO ????????????????????????
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

        altId = map.getString("altId");

        // created-date maybe overrided by DAOs.
        Date createdDate = map.getDate("createdDate");
        if (createdDate != null)
            setCreatedDate(createdDate);

        // last-modified maybe overrided by DAOs.
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

        if (createdDateDirty)
            map.put("createdDate", createdDate);

        if (lastModifiedDirty)
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
        marshalAs(MarshalType.ID_REF);

        this.id = id;
        this.version = null;

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
    @Override
    public <self_t extends BaseDto<?>> self_t ref(E entity) {
        // 1, Ref-by-id by default.
        // 2. Don't set ID_VER_REF here.
        marshalAs(MarshalType.ID_REF);

        if (entity == null) {
            this.setId(null);
            this.setVersion(null);
            this.setNull(true);
        } else {
            this.setId(entity.getId());
            this.setVersion(entity.getVersion());
            this.setNull(false);
        }

        _track(entity);

        @SuppressWarnings("unchecked")
        self_t self = (self_t) this;
        return self;
    }

    public <self_t extends EntityDto<E, K>> self_t ref(self_t dto) {
        if (dto == null)
            throw new NullPointerException("dto");

        marshalAs(MarshalType.ID_REF); // ID_VER_REF

        setId(dto.getId());
        setVersion(dto.getVersion());

        defaultPage = dto.defaultPage;

        @SuppressWarnings("unchecked")
        self_t self = (self_t) this;
        return self;
    }

    void _track(E source) {
        if (source == null) {
            defaultPage = null;
            return;
        }

        IObjectPageDirectory pageDir = PageDirectory.getPageDirectory(source);
        if (pageDir == null) {
            logger.warn("No page for entity type: " + sourceType);
            return;
        }

        Map<String, Object> viewParams = new HashMap<String, Object>();
        viewParams.put("id", source.getId());

        List<Location> locations = pageDir.getPagesForView(StandardViews.CONTENT, viewParams);
        if (locations.isEmpty())
            logger.warn("No content view for entity type: " + sourceType);
        else {
            Location location = locations.get(0);
            defaultPage = new LocationVmap(location);
        }
    }

    @Override
    protected E mergeDeref(E givenTarget) {
        Class<? extends E> entityType = sourceType;

        if (isNullRef())
            return null;

        switch (marshalType) {
        case ID_REF:
            if (givenTarget != null) {
                // Return the given entity immediately if id matches.
                K givenId = givenTarget.getId();
                if (Nullables.equals(id, givenId))
                    return givenTarget;
            }

            try {
                return ctx.data.getRef(entityType, id);
            } catch (HibernateSystemException e) {
                throw e;
            }

        case ID_VER_REF:
            if (version == null)
                throw new IllegalUsageException("ID-VER-REF but version isn't set.");

            E entity;
            try {
                entity = ctx.data.getRef(entityType, id);
            } catch (HibernateSystemException e) {
                throw e;
            }

            if (!version.equals(entity.getVersion()))
                throw new IllegalStateException("ID-VER-REF but version of entity has been changed.");

            return entity;

        case NAME_REF:
        case OTHER_REF:
        default:
            throw new NotImplementedException("REF by name or other property isn't supportet.");

        case SELECTION:
            if (givenTarget != null) // ignore thisDto.id
                return givenTarget;

            boolean partialMerge;
            if (isNewCreated())
                partialMerge = false;
            else
                partialMerge = id != null;

            if (partialMerge) {
                E existing = ctx.data.access(entityType).get(id);
                if (existing != null)
                    return existing;
            }

            E newEntity;
            try {
                newEntity = entityType.newInstance();
            } catch (ReflectiveOperationException e) {
                throw new IllegalUsageException("Failed to instantiate source bean " + entityType.getName(), e);
            }
            return newEntity;
        } // switch
    }

    /**
     * @see Entity#idEquals(EntityBase)
     */
    @Override
    protected final boolean idEquals(BaseDto<E> other) {
        @SuppressWarnings("unchecked")
        EntityDto<E, K> o = (EntityDto<E, K>) other;
        return idEquals(o);
    }

    /**
     * @see Entity#idEquals(EntityBase)
     */
    protected final boolean idEquals(EntityDto<E, K> other) {
        K id1 = getId();
        K id2 = other.getId();
        return Nullables.equals(id1, id2);
    }

    /**
     * @see Entity#idHashCode()
     */
    @Override
    protected final int idHashCode() {
        int hash = 0;
        K id = getId();
        if (id != null)
            hash += id.hashCode();
        return hash;
    }

    @Override
    protected Serializable naturalId() {
        // K id = getId();
        // if (id == null)
        // return new Identity(this);
        // else
        // return id;
        return null;
    }

    @Override
    public void toString(PrettyPrintStream out, FormatStyle format, Set<Object> occurred, int depth) {
        EntityDtoFormatter formatter = new EntityDtoFormatter(out, occurred);
        formatter.format(this, format, depth);
    }

    // Properties

    public LocationVmap getDefaultPage() {
        return defaultPage;
    }

    public String getClickAction() {
        if (defaultPage == null)
            return null;
        else {
            String href = defaultPage.toString();
            String js = "return _open('" + href + "');";
            return js;
        }
    }

    public int get_index() {
        return _index;
    }

    public void set_index(int _index) {
        this._index = _index;
    }

    public Boolean get_checked() {
        return _checked;
    }

    public void set_checked(Boolean checked) {
        this._checked = checked;
    }

    /**
     * unchecked by default.
     */
    public boolean isChecked0() {
        return _checked != Boolean.TRUE;
    }

    public void setChecked0(boolean checked) {
        _checked = checked;
    }

    /**
     * checked by default.
     */
    public boolean isChecked1() {
        return _checked != Boolean.FALSE;
    }

    public void setChecked1(boolean checked) {
        _checked = checked;
    }

    /**
     * Get ID.
     *
     * @return <code>null</code> If id isn't set. Thus should be skipped.
     */
    @NotNull
    @Override
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

    public void setId(K id, boolean skipped) {
        this.id = id;
        this._skipId = skipped;
    }

    public final K getId_O() {
        return getId();
    }

    public final void setId_O(K id) {
        if (id == null)
            throw new NullPointerException("id");
        setId(id);
    }

    /**
     * Get optional ID, and translate null to zero.
     *
     * @return Int32 value of ID, returns zero if id is missing.
     */
    public final int getId_OZ() {
        if (id == null)
            return 0;
        else
            return (Integer) id;
    }

    @SuppressWarnings("unchecked")
    public final void setId_OZ(int id) {
        if (id == 0)
            this.id = null;
        else
            this.id = (K) (Integer) id;
    }

    public final long getId_OZL() {
        if (id == null)
            return 0L;
        else
            return (Long) id;
    }

    @SuppressWarnings("unchecked")
    public final void setId_OZL(long id) {
        if (id == 0L)
            this.id = null;
        else
            this.id = (K) (Long) id;
    }

    public final String getId_OE() {
        if (id == null)
            return "";
        else
            return (String) id;
    }

    @SuppressWarnings("unchecked")
    public final void setId_OE(String id) {
        if (id != null && id.trim().isEmpty())
            this.id = null;
        else
            this.id = (K) id;
    }

    // Require-Validation enabled:

    @RequiredId
    public final K getId_R() {
        return getId_O();
    }

    public final void setId_R(K id) {
        setId_O(id);
    }

    @RequiredId(zeroForNull = true)
    public final int getId_RZ() {
        return getId_OZ();
    }

    public final void setId_RZ(int id) {
        setId_OZ(id);
    }

    @RequiredId(zeroForNull = true)
    public final long getId_RZL() {
        return getId_OZL();
    }

    public final void setId_RZL(long id) {
        setId_OZL(id);
    }

    @RequiredId(emptyForNull = true)
    public final String getId_RE() {
        return getId_OE();
    }

    public final void setId_RE(String id) {
        setId_OE(id);
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

    public String getAltId() {
        return altId;
    }

    public void setAltId(String altId) {
        this.altId = altId;
    }

    public/* final */int getStateInt() {
        return stateInt;
    }

    public/* final */void setStateInt(int stateInt) {
        if (this.stateInt != stateInt) {
            this.stateInt = stateInt;
            pfm = null;
        }
    }

    public PropetyFlagsMap getPfm() {
        if (pfm == null) {
            Class<? extends E> entityType = getEntityType();
            StatePropertyFlagsMapMap stateMap = StatePropertyFlagsMapMap.forClass(entityType);
            pfm = stateMap.getPropertyFlagsMap(stateInt);
            fillPropertyFlagsMap(stateInt, pfm);
        }
        return pfm;
    }

    /**
     * Example:
     *
     * <pre>
     * if (state == STATE_FIN_DONE)
     *     pfm.setLockedForAllProperties(true);
     * </pre>
     */
    protected void fillPropertyFlagsMap(int state, PropetyFlagsMap pfm) {
    }

    protected String getName() {
        throw new NotImplementedException();
    }

    protected void setName(String name) {
        throw new NotImplementedException();
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
        this.createdDateDirty = true;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
        this.lastModifiedDirty = true;
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

    protected static <S extends StandardSamples> S predefined(Class<? extends S> samplesClass) {
        S samples = HttpAssembledContext.bean.getBean(samplesClass);
        return samples;
    }

}
