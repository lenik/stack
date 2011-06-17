package com.bee32.plover.arch.util;

import java.beans.IntrospectionException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.free.IllegalUsageException;
import javax.free.NotImplementedException;
import javax.free.ParseException;
import javax.free.TypeConvertException;
import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.parser.Entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

public abstract class BaseDto<S, C>
        extends BaseDto_ASM<S, C> {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(BaseDto.class);

    protected Class<? extends S> sourceType;
    protected final Flags32 selection = new Flags32();

    protected MarshalType marshalType = MarshalType.SELECTION;
    protected boolean nullRef;

    protected BaseDto(Class<? extends S> sourceType) {
        initSourceType(sourceType);
    }

    public BaseDto() {
        initSourceType(ClassUtil.<S> infer1(getClass(), BaseDto.class, 0));
    }

    public BaseDto(S source) {
        this();
        if (source != null)
            marshal(source);
    }

    public BaseDto(int selection) {
        this();
        this.selection.set(selection);
    }

    public BaseDto(int selection, S source) {
        this(selection);
        if (source != null)
            marshal(source);
    }

    /**
     * When it's a generic DTO, then the source type is not specialized yet. So there should be a
     * chance to change it.
     *
     * One should override {@link #initSourceType(Class)} as public so it may be changed by user.
     *
     * @param sourceType
     *            New source type.
     */
    @SuppressWarnings("unchecked")
    protected void initSourceType(Class<? extends S> sourceType) {
        // XXX cast?
        this.sourceType = (Class<S>) sourceType;
    }

    public MarshalType getMarshalType() {
        return marshalType;
    }

    public boolean isNullRef() {
        return nullRef && marshalType.isReference();
    }

    public void marshalAs(MarshalType marshalType) {
        if (marshalType == null)
            throw new NullPointerException("marshalType");
        this.marshalType = marshalType;
    }

    protected C getDefaultMarshalContext() {
        return null;
    }

    protected IMarshalSession getSession() {
        if (sessionStack == null || sessionStack.isEmpty())
            throw new IllegalStateException("No marshal session.");
        return sessionStack.lastElement();
    }

    // [A] Context/Session wrapper

    public final <D extends BaseDto<S, C>> D marshal(C context, S source) {
        return marshal(createOrReuseSession(context), source);
    }

    public final S merge(C context, S source) {
        return merge(createOrReuseSession(context), source);
    }

    public final void parse(C context, Map<String, ?> map)
            throws ParseException {
        parse(createOrReuseSession(context), map);
    }

    public final void export(C context, Map<String, Object> map) {
        export(createOrReuseSession(context), map);
    }

    // [B] TLS-Context/Session wrapper

    public final <D extends BaseDto<S, C>> D marshal(S source) {
        return marshal(getDefaultMarshalContext(), source);
    }

    public final S merge(S source) {
        return merge(getDefaultMarshalContext(), source);
    }

    public final void parse(Map<String, ?> map)
            throws ParseException {
        parse(getDefaultMarshalContext(), map);
    }

    public final void export(Map<String, Object> map) {
        export(getDefaultMarshalContext(), map);
    }

    /**
     * <pre>
     * LAYER 2
     * -----------------------------------------------------------------------
     *      This layer contains the skeleton implementations.
     *
     *      These methods are package-local.
     *      The skeleton is overall fixed in the whole project.
     * </pre>
     */

    /**
     * Marshal skeleton implementation.
     */
    final <D extends IDataTransferObject<S, C>> D marshalImpl(S source) {
        @SuppressWarnings("unchecked")
        D _this = (D) this;

        if (source == null) {
            marshalType = MarshalType.ID_REF;

            return _this;
        }

        // Do the real marshal work.
        // logger.debug("marshal begin");

        __marshal(source);
        _marshal(source);

        // logger.debug("marshal end");

        marshalType = MarshalType.SELECTION;

        return _this;
    }

    /**
     * Read some internal properties from the specified source bean into this object.
     *
     * @param source
     *            Non-null source source whose properties are read into this object.
     */
    protected void __marshal(S source) {
    }

    /**
     * Read some user properties from the specified source bean into this object.
     *
     * @param source
     *            Non-null source source whose properties are read into this object.
     */
    protected abstract void _marshal(S source);

    protected S mergeDeref(S given) {
        if (given == null)
            try {
                given = sourceType.newInstance();
            } catch (ReflectiveOperationException e) {
                throw new IllegalUsageException("Failed to instantiate source bean " + sourceType.getName(), e);
            }
        return given;
    }

    final S mergeImpl(S target) {
        if (isNullRef())
            return null;

        S deref = mergeDeref(target);

        if (marshalType == MarshalType.SELECTION) {
            __unmarshalTo(deref);
            _unmarshalTo(deref);
        }

        return deref;
    }

    /**
     * Write some internal properties from this object into the specified source bean.
     *
     * @param context
     *            Non-<code>null</code> unmarshal context.
     * @param target
     *            Non-<code>null</code> target source bean.
     * @throws NotImplementedException
     *             If unmarshal isn't supported for this DTO.
     */
    protected void __unmarshalTo(S target) {
    }

    /**
     * Write some user properties from this object into the specified source bean.
     *
     * @param context
     *            Non-<code>null</code> unmarshal context.
     * @param target
     *            Non-<code>null</code> target source bean.
     * @throws NotImplementedException
     *             If unmarshal isn't supported for this DTO.
     */
    protected abstract void _unmarshalTo(S target);

    final void parseImpl(TextMap map)
            throws ParseException {
        __parse(map);
        _parse(map);
        marshalType = MarshalType.SELECTION;
    }

    protected void __parse(TextMap map)
            throws ParseException {
    }

    /**
     * Parse parameters from a variant lookup map.
     *
     * @throws TypeConvertException
     *             If type conversion failure inside the variant lookup map.
     * @throws ParseException
     *             Other parse exception caused by user implementation.
     */
    protected abstract void _parse(TextMap map)
            throws ParseException;

    final void exportImpl(Map<String, Object> map) {
        __export(map);
        _export(map);
    }

    protected void __export(Map<String, Object> map) {
    }

    protected void _export(Map<String, Object> map) {
        exportProperties(map);
    }

    /**
     * <pre>
     * LAYER 3 - STATIC HELPER: MARSHAL
     * -----------------------------------------------------------------------
     *      This layer deals with generic marshal,
     *      for both scalar and collections.
     * </pre>
     */

    /**
     * Generic marshal with nullable source support.
     */
    public static <S, D extends BaseDto<S, C>, C> D marshal(IMarshalSession session, //
            Class<D> dtoClass, Integer selection, S source) {
        D dto;
        try {
            if (selection == null)
                dto = dtoClass.newInstance();
            else {
                Constructor<D> ctor = dtoClass.getConstructor(int.class);
                dto = ctor.newInstance(selection.intValue());
            }
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to instantiate DTO " + dtoClass.getName(), e);
        }

        // Do the marshal.
        // the marshal() function will deal with null carefully.
        if (session == null)
            dto = dto.marshal(source);
        else
            dto = dto.marshal(session, source);

        return dto;
    }

    public static <S, D extends BaseDto<S, C>, C> D marshal(IMarshalSession session, Class<D> dtoClass, S source) {
        return marshal(session, dtoClass, null, source);
    }

    public static <S, D extends BaseDto<S, C>, C> D marshal(Class<D> dtoClass, Integer selection, S source) {
        return marshal(null, dtoClass, selection, source);
    }

    public static <S, D extends BaseDto<S, C>, C> D marshal(Class<D> dtoClass, S source) {
        return marshal(null, dtoClass, null, source);
    }

    public static <S, D extends BaseDto<S, C>, C> List<D> marshalList(IMarshalSession session, Class<D> dtoClass,
            Integer selection, Iterable<? extends S> sources) {

        List<D> dtoList = new ArrayList<D>();

        if (sources == null)
            return dtoList;

        for (S _source : sources) {
            D dto = marshal(session, dtoClass, selection, _source);
            dtoList.add(dto);
        }

        return dtoList;
    }

    public static <S, D extends BaseDto<S, C>, C> List<D> marshalList(//
            IMarshalSession session, Class<D> dtoClass, Iterable<? extends S> sources) {
        return marshalList(session, dtoClass, null, sources);
    }

    public static <S, D extends BaseDto<S, C>, C> List<D> marshalList(//
            Class<D> dtoClass, Integer selection, Iterable<? extends S> sources) {
        return marshalList(null, dtoClass, selection, sources);
    }

    public static <S, D extends BaseDto<S, C>, C> List<D> marshalList(//
            Class<D> dtoClass, Iterable<? extends S> sources) {
        return marshalList(null, dtoClass, null, sources);
    }

    public static <S, D extends BaseDto<S, C>, C> Set<D> marshalSet(//
            IMarshalSession session, Class<D> dtoClass, Integer selection, Iterable<? extends S> sources) {

        Set<D> dtoSet = new HashSet<D>();

        if (sources == null)
            return dtoSet;

        for (S _source : sources) {
            D dto = marshal(session, dtoClass, selection, _source);
            dtoSet.add(dto);
        }

        return dtoSet;
    }

    public static <S, D extends BaseDto<S, C>, C> Set<D> marshalSet(//
            IMarshalSession session, Class<D> dtoClass, Iterable<? extends S> sources) {
        return marshalSet(session, dtoClass, null, sources);
    }

    public static <S, D extends BaseDto<S, C>, C> Set<D> marshalSet(//
            Class<D> dtoClass, Integer selection, Iterable<? extends S> sources) {
        return marshalSet(null, dtoClass, selection, sources);
    }

    public static <S, D extends BaseDto<S, C>, C> Set<D> marshalSet(//
            Class<D> dtoClass, Iterable<? extends S> sources) {
        return marshalSet(null, dtoClass, null, sources);
    }

    /**
     * <pre>
     * LAYER 3 - STATIC HELPER: UNMARSHAL
     * -----------------------------------------------------------------------
     *      Unmarshal collections.
     * </pre>
     */

    /**
     * In the base DTO implementation, no modification / ref is used.
     */
    public static <Coll extends Collection<S>, D extends BaseDto<S, C>, S, C> //
    /*    */Coll _unmarshalCollection(IMarshalSession session, Coll collection, Iterable<? extends D> dtoList) {

        if (collection == null)
            throw new NullPointerException("collection");

        collection.clear();

        if (dtoList != null)
            for (D dto : dtoList) {
                if (dto == null)
                    throw new NullPointerException("dto");

                S source = dto.merge(session, null);

                collection.add(source);
            }

        return collection;
    }

    public static <S, D extends BaseDto<S, C>, C> //
    /*    */List<S> _unmarshalList(IMarshalSession session, Iterable<? extends D> dtoList) {
        return _unmarshalCollection(session, new ArrayList<S>(), dtoList);
    }

    public static <S, D extends BaseDto<S, C>, C> //
    /*    */Set<S> _unmarshalSet(IMarshalSession session, Iterable<? extends D> dtoList) {
        return _unmarshalCollection(session, new HashSet<S>(), dtoList);
    }

    public static <Coll extends Collection<S>, D extends BaseDto<S, C>, S, C> //
    /*    */Coll _unmershalCollection(Coll collection, Iterable<? extends D> dtoList) {
        return _unmarshalCollection(null, collection, dtoList);
    }

    public static <S, D extends BaseDto<S, C>, C> //
    /*    */List<S> _unmershalList(Iterable<? extends D> dtoList) {
        return _unmarshalCollection(null, new ArrayList<S>(), dtoList);
    }

    public static <S, D extends BaseDto<S, C>, C> //
    /*    */Set<S> _unmarshalSet(Iterable<? extends D> dtoList) {
        return _unmarshalCollection(null, new HashSet<S>(), dtoList);
    }

    /**
     * <pre>
     * LAYER 4 - PROPERTY MERGE
     * -----------------------------------------------------------------------
     *      merge(propertyAccessor)
     *      merge("property")
     * </pre>
     */
    public static <S, _s, C> void merge(IMarshalSession session, S target, //
            IPropertyAccessor<S, _s> property, BaseDto<_s, C> propertyDto) {

        // DTO == null means ignore.
        if (propertyDto == null)
            return;

        _s _old = property.get(target);
        _s _new;

        if (session == null)
            _new = propertyDto.merge(_old);
        else
            _new = propertyDto.merge(session, _old);

        if (_new != _old)
            property.set(target, _new);
    }

    public static <S, _s, C> void merge(S target, //
            IPropertyAccessor<S, _s> property, BaseDto<_s, C> propertyDto) {
        merge(null, target, property, propertyDto);
    }

    public static <S, _s, C> void merge(IMarshalSession session, S target, //
            String propertyName, BaseDto<_s, C> propertyDto) {

        // DTO == null means ignore.
        if (propertyDto == null)
            return;

        Class<S> targetType = (Class<S>) target.getClass();

        IPropertyAccessor<S, _s> property;
        try {
            property = BeanPropertyAccessor.access(targetType, propertyName);
        } catch (IntrospectionException e) {
            // XXX Error message?
            throw new IllegalUsageException(e.getMessage(), e);
        }

        merge(session, target, property, propertyDto);
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
     * <li>It has been {@link BaseDto#marshal(Object) marhsalled} from an entity bean.
     * <li>It has {@link BaseDto#parse(Map) parsed from a Map}, or
     * {@link BaseDto#parse(HttpServletRequest) from an HttpServletRequest}.</li>
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
     *             If data access exception happened with calls into the
     *             {@link IEntityMarshalContext}.
     */
    public static <S, _s, C> void merge(S target, //
            String propertyName, BaseDto<_s, C> propertyDto) {
        // IMarshalSession session = getSession();
        merge(null, target, propertyName, propertyDto);
    }

}
