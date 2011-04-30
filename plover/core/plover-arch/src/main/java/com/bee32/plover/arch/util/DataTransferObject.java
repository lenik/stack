package com.bee32.plover.arch.util;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.free.IllegalUsageException;
import javax.free.NotImplementedException;
import javax.free.ParseException;
import javax.free.TypeConvertException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DataTransferObject<T, C>
        implements IDataTransferObject<T, C>, Serializable {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(DataTransferObject.class);

    protected Class<? extends T> sourceType;

    protected final Flags32 selection = new Flags32();

    protected boolean filled;

    protected DataTransferObject(Class<? extends T> sourceType) {
        _setSourceType(sourceType);
    }

    public DataTransferObject() {
        _setSourceType(ClassUtil.<T> infer1(getClass(), DataTransferObject.class, 0));
    }

    public DataTransferObject(int selection) {
        this();
        this.selection.set(selection);
    }

    public DataTransferObject(T source) {
        this();

        if (source != null)
            marshal(source);
    }

    public DataTransferObject(int selection, T source) {
        this(selection);

        if (source != null)
            marshal(source);
    }

    /**
     * When it's a generic DTO, then the source type is not specialized yet. So there should be a
     * chance to change it.
     *
     * One should override {@link #_setSourceType(Class)} as public so it may be changed by user.
     *
     * @param sourceType
     *            New source type.
     */
    @SuppressWarnings("unchecked")
    protected void _setSourceType(Class<? extends T> sourceType) {
        // XXX cast?
        this.sourceType = (Class<T>) sourceType;
    }

    public final boolean _isFilled() {
        return filled;
    }

    public void _setFilled(boolean filled) {
        this.filled = filled;
    }

    private static ThreadLocal<Map<Object, Object>> threadLocalGraph;
    static {
        threadLocalGraph = new ThreadLocal<Map<Object, Object>>();
    }

    protected C _getDefaultContext() {
        return null;
    }

    @Override
    public final <$ extends DataTransferObject<T, ?>> $ marshalRec(T source) {
        @SuppressWarnings("unchecked")
        $ _this = ($) this;

        if (source == null) {
            filled = false;
            return _this;
        }

        Map<Object, Object> graph = threadLocalGraph.get();
        boolean topLevel = graph == null;
        if (topLevel) {
            graph = new IdentityHashMap<Object, Object>();
            threadLocalGraph.set(graph);
        }

        $ marshalledFlyWeight = ($) graph.get(source);
        if (marshalledFlyWeight != null)
            return marshalledFlyWeight;

        graph.put(source, this);

        try {
            return marshal(source);
        } finally {
            if (topLevel)
                threadLocalGraph.remove();
        }
    }

    public final <$ extends DataTransferObject<T, ?>> $ marshal(T source) {
        @SuppressWarnings("unchecked")
        $ _this = ($) this;

        if (source == null) {
            filled = false;
            return _this;
        }

        // Do the real marshal work.
        // logger.debug("marshal begin");

        __marshal(source);
        _marshal(source);

        // logger.debug("marshal end");

        filled = true;

        return _this;
    }

    /**
     * Read some internal properties from the specified source bean into this object.
     *
     * @param source
     *            Non-null source source whose properties are read into this object.
     */
    protected void __marshal(T source) {
    }

    /**
     * Read some user properties from the specified source bean into this object.
     *
     * @param source
     *            Non-null source source whose properties are read into this object.
     */
    protected abstract void _marshal(T source);

    public static <D extends DataTransferObject<T, ?>, T> T unmarshal(D dto) {
        if (dto == null)
            return null;
        else
            return dto.unmarshal();
    }

    public static <D extends DataTransferObject<T, C>, T, C> T unmarshal(C context, D dto) {
        if (dto == null)
            return null;
        else
            return dto.unmarshal(context);
    }

    @Override
    public final T unmarshal() {
        return unmarshal((C) null);
    }

    @Override
    public final void unmarshalTo(T target) {
        unmarshalTo((C) null, target);
    }

    @Override
    public T unmarshal(C context) {
        T target;

        try {
            target = sourceType.newInstance();
        } catch (ReflectiveOperationException e) {
            throw new IllegalUsageException("Failed to instantiate source bean " + sourceType.getName(), e);
        }

        unmarshalTo(context, target);
        return target;
    }

    @Override
    public final void unmarshalTo(C context, T target) {
        if (!filled) {
            logger.warn("Unmarshal is ignored: unmarshalTo() is called but the DTO is not filled. "
                    + "You should use unmarshal() instead, to unmarshal non-filled DTOs.");
            return;
        }

        if (context == null)
            context = _getDefaultContext();

        __unmarshalTo(context, target);
        _unmarshalTo(context, target);
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
    protected void __unmarshalTo(C context, T target) {
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
    protected abstract void _unmarshalTo(C context, T target);

    /**
     * Parse parameters from servlet-request, and put the parsed result into this object.
     *
     * @param request
     *            Non-<code>null</code> request object.
     * @throws ServletException
     *             If parse errors.
     */
    public final void parse(HttpServletRequest request)
            throws ServletException {

        TextMap textMap = TextMap.convert(request);

        try {
            parse(textMap);
        } catch (TypeConvertException e) {
            throw new ServletException("Parse error: " + e.getMessage(), e);
        } catch (ParseException e) {
            throw new ServletException("Parse error: " + e.getMessage(), e);
        }
    }

    /**
     * Parse parameters from a map, and put the parsed result into this object.
     *
     * @param map
     *            Non-<code>null</code> request object.
     */
    public final void parse(Map<String, ?> map)
            throws ParseException {
        if (map == null)
            throw new NullPointerException("map");

        TextMap textMap = TextMap.convert(map);

        try {
            parse(textMap);
        } catch (TypeConvertException e) {
            throw new ParseException(e.getMessage(), e);
        }
    }

    public final void parse(TextMap map)
            throws ParseException {
        __parse(map);
        _parse(map);
        filled = true;
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

    public Map<String, Object> exportMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        export(map);
        return map;
    }

    /**
     * Export to a map.
     */
    public void export(Map<String, Object> map) {
    }

    public static <D extends DataTransferObject<T, ?>, T> D marshal(Class<D> dtoClass, Integer selection, T source) {
        if (source == null)
            return null;
        try {
            D dto;
            if (selection == null)
                dto = dtoClass.newInstance();
            else {
                Constructor<D> ctor = dtoClass.getConstructor(int.class);
                dto = ctor.newInstance(selection.intValue());
            }

            // Do the marshal.
            dto.marshal(source);

            return dto;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to instantiate DTO " + dtoClass.getName(), e);
        }
    }

    public static <D extends DataTransferObject<T, ?>, T> D marshalRec(Class<D> dtoClass, Integer selection, T source) {
        if (source == null)
            return null;
        try {
            D dto;
            if (selection == null)
                dto = dtoClass.newInstance();
            else {
                Constructor<D> ctor = dtoClass.getConstructor(int.class);
                dto = ctor.newInstance(selection.intValue());
            }

            // Do the marshal.
            dto.marshalRec(source);

            return dto;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to instantiate DTO " + dtoClass.getName(), e);
        }
    }

    public static <D extends DataTransferObject<T, ?>, T> D marshal(Class<D> dtoClass, T source) {
        return marshal(dtoClass, null, source);
    }

    public static <D extends DataTransferObject<T, ?>, T> D marshalRec(Class<D> dtoClass, T source) {
        return marshalRec(dtoClass, null, source);
    }

    /**
     * Marshal each source to a DTO object.
     *
     * @param dtoClass
     *            The DTO type to be marshalled to.
     * @param values
     *            Entity array to be marshalled.
     * @return <code>null</code>.
     */
    public static <D extends DataTransferObject<T, ?>, T> List<D> marshalList(Class<D> dtoClass, T... values) {
        List<T> sourceList = Arrays.asList(values);
        return marshalList(dtoClass, null, sourceList);
    }

    /**
     * Marshal each source to a DTO object.
     *
     * @param dtoClass
     *            The DTO type to be marshalled to.
     * @param values
     *            Entity array to be marshalled.
     * @return <code>null</code>.
     */
    public static <D extends DataTransferObject<T, ?>, T> List<D> marshalListRec(Class<D> dtoClass, T... values) {
        List<T> sourceList = Arrays.asList(values);
        return marshalListRec(dtoClass, null, sourceList);
    }

    /**
     * Marshal each source to a DTO object.
     *
     * @param dtoClass
     *            The DTO type to be marshalled to.
     * @param selection
     *            Selection for each source.
     * @param values
     *            Entity array to be marshalled.
     * @return <code>null</code>.
     */
    public static <D extends DataTransferObject<T, ?>, T> List<D> marshalList(Class<D> dtoClass, int selection,
            T... values) {
        List<T> sourceList = Arrays.asList(values);
        return marshalList(dtoClass, selection, sourceList);
    }

    /**
     * Marshal each source to a DTO object.
     *
     * @param dtoClass
     *            The DTO type to be marshalled to.
     * @param selection
     *            Selection for each source.
     * @param values
     *            Entity array to be marshalled.
     * @return <code>null</code>.
     */
    public static <D extends DataTransferObject<T, ?>, T> List<D> marshalListRec(Class<D> dtoClass, int selection,
            T... values) {
        List<T> sourceList = Arrays.asList(values);
        return marshalListRec(dtoClass, selection, sourceList);
    }

    /**
     * Marshal each source to a DTO object.
     *
     * @param dtoClass
     *            The DTO type to be marshalled to.
     * @param values
     *            Entities to be marshalled.
     * @return <code>null</code>.
     */
    public static <D extends DataTransferObject<T, ?>, T> List<D> marshalList(Class<D> dtoClass, Iterable<?> values) {
        return _marshalList(false, dtoClass, null, values);
    }

    public static <D extends DataTransferObject<T, ?>, T> List<D> marshalListRec(Class<D> dtoClass, Iterable<?> values) {
        return _marshalList(true, dtoClass, null, values);
    }

    /**
     * Marshal each source to a DTO object.
     *
     * @param dtoClass
     *            The DTO type to be marshalled to.
     * @param selection
     *            Selection for each source.
     * @param values
     *            Entities to be marshalled.
     * @return <code>null</code> if <code>values</code> is <code>null</code>.
     */
    public static <D extends DataTransferObject<T, ?>, T> List<D> marshalList(Class<D> dtoClass, Integer selection,
            Iterable<?> values) {
        return _marshalList(false, dtoClass, selection, values);
    }

    public static <D extends DataTransferObject<T, ?>, T> List<D> marshalListRec(Class<D> dtoClass, Integer selection,
            Iterable<?> values) {
        return _marshalList(true, dtoClass, selection, values);
    }

    static <D extends DataTransferObject<T, ?>, T> List<D> _marshalList(boolean rec, Class<D> dtoClass,
            Integer selection, Iterable<?> values) {

        List<D> dtoList = new ArrayList<D>();

        if (values == null)
            return dtoList;

        try {
            for (Object _source : values) {
                D dto;
                if (_source == null)
                    dto = null;
                else {
                    @SuppressWarnings("unchecked")
                    T source = (T) _source;

                    dto = dtoClass.newInstance();
                    if (selection != null)
                        dto.selection.init(selection);

                    dto.marshal(source);
                }
                dtoList.add(dto);
            }
        } catch (ReflectiveOperationException e) {
            throw new IllegalUsageException("Failed to instantiate " + dtoClass.getName(), e);
        }

        return dtoList;
    }

    /**
     * It's not append, but assign to the collection.
     */
    public static <Coll extends Collection<T>, D extends DataTransferObject<T, C>, T, C> //
    /*    */Coll unmarshal(C context, Coll collection, Iterable<? extends D> dtoList) {

        if (collection == null)
            throw new NullPointerException("collection");

        collection.clear();

        if (dtoList != null)
            for (D dto : dtoList) {
                T source;
                if (dto == null)
                    source = null;
                else
                    source = dto.unmarshal(context);
                collection.add(source);
            }

        return collection;
    }

    public static <Coll extends Collection<T>, D extends DataTransferObject<T, C>, T, C> //
    /*    */Coll unmarshal(Coll collection, Iterable<? extends D> dtoList) {
        return unmarshal((C) null, collection, dtoList);
    }

    public static <D extends DataTransferObject<T, C>, T, C> //
    /*    */List<T> unmarshalList(Iterable<? extends D> dtoList) {
        return unmarshal(null, new ArrayList<T>(), dtoList);
    }

    public static <D extends DataTransferObject<T, C>, T, C> //
    /*    */List<T> unmarshalList(C context, Iterable<? extends D> dtoList) {
        return unmarshal(context, new ArrayList<T>(), dtoList);
    }

    public static <D extends DataTransferObject<T, C>, T, C> //
    /*    */Set<T> unmarshalSet(Iterable<? extends D> dtoList) {
        return unmarshal(null, new HashSet<T>(), dtoList);
    }

    public static <D extends DataTransferObject<T, C>, T, C> //
    /*    */Set<T> unmarshalSet(C context, Iterable<? extends D> dtoList) {
        return unmarshal(context, new HashSet<T>(), dtoList);
    }

}
