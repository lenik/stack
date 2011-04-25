package com.bee32.plover.arch.util;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.free.AbstractVariantLookupMap;
import javax.free.Dates;
import javax.free.IVariantLookupMap;
import javax.free.IllegalUsageException;
import javax.free.Map2VariantLookupMap;
import javax.free.NotImplementedException;
import javax.free.ParseException;
import javax.free.TypeConvertException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public abstract class DataTransferObject<T, C>
        implements IDataTransferObject<T, C>, Serializable {

    private static final long serialVersionUID = 1L;

    protected Class<? extends T> sourceType;

    protected final Flags32 selection = new Flags32();

    protected DataTransferObject(Class<? extends T> sourceType) {
        setSourceType(sourceType);
    }

    public DataTransferObject() {
        setSourceType(ClassUtil.<T> infer1(getClass(), DataTransferObject.class, 0));
    }

    public DataTransferObject(int selection) {
        this();
        this.selection.set(selection);
    }

    public DataTransferObject(T source) {
        this();

        if (source == null)
            throw new NullPointerException("source");

        marshal(source);
    }

    public DataTransferObject(T source, int selection) {
        this(selection);

        if (source == null)
            throw new NullPointerException("source");

        marshal(source);
    }

    public void setSourceType(Class<? extends T> sourceType) {
        this.sourceType = sourceType;
    }

    private static ThreadLocal<Map<Object, Object>> threadLocalGraph;
    static {
        threadLocalGraph = new ThreadLocal<Map<Object, Object>>();
    }

    protected C defaultContext() {
        return null;
    }

    @Override
    public final <D extends DataTransferObject<T, ?>> D marshalRec(T source) {
        if (source == null)
            return null;

        Map<Object, Object> graph = threadLocalGraph.get();
        boolean topLevel = graph == null;
        if (topLevel) {
            graph = new IdentityHashMap<Object, Object>();
            threadLocalGraph.set(graph);
        }

        D marshalledFlyWeight = (D) graph.get(source);
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

    public final <D extends DataTransferObject<T, ?>> D marshal(T source) {
        if (source == null)
            return null;

        // Do the real marshal work.
        // logger.debug("marshal begin");

        __marshal(source);
        _marshal(source);

        // logger.debug("marshal end");

        @SuppressWarnings("unchecked")
        D self = (D) this;

        return self;
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

    @Override
    public final T unmarshal() {
        return unmarshal((C) null);
    }

    @Override
    public final void unmarshalTo(T target) {
        unmarshalTo((C) null, target);
    }

    @Override
    public final T unmarshal(C context) {
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
        if (context == null)
            context = defaultContext();

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
        try {
            Map<String, ?> requestMap = request.getParameterMap();
            ReqLookMap lookupMap = new ReqLookMap(requestMap);
            parse(lookupMap);
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
        try {
            parse(new Map2VariantLookupMap<String>(map));
        } catch (TypeConvertException e) {
            throw new ParseException(e.getMessage(), e);
        }
    }

    /**
     * Parse parameters from a variant lookup map.
     *
     * @throws TypeConvertException
     *             If type conversion failure inside the variant lookup map.
     * @throws ParseException
     *             Other parse exception caused by user implementation.
     */
    public void parse(IVariantLookupMap<String> map)
            throws ParseException, TypeConvertException {
    }

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

    public static <D extends DataTransferObject<T, ?>, T> D marshal(Class<D> dtoClass, T source) {
        if (source == null)
            return null;
        try {
            D dto = dtoClass.newInstance();
            dto.marshal(source);
            return dto;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to instantiate DTO " + dtoClass.getName(), e);
        }
    }

    public static <D extends DataTransferObject<T, ?>, T> D marshalRec(Class<D> dtoClass, T source) {
        if (source == null)
            return null;
        try {
            D dto = dtoClass.newInstance();
            dto.marshalRec(source);
            return dto;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to instantiate DTO " + dtoClass.getName(), e);
        }
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

        if (values == null)
            return null;

        List<D> dtoList = new ArrayList<D>();

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

    public static <Coll extends Collection<T>, D extends DataTransferObject<T, C>, T, C> //
    /*    */Coll unmarshal(C context, Coll collection, Iterable<? extends D> dtoList) {

        if (dtoList == null)
            return null;

        for (DataTransferObject<T, C> dto : dtoList) {
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

class ReqLookMap
        extends AbstractVariantLookupMap<String> {

    Map<String, ?> map;

    public ReqLookMap(Map<String, ?> map) {
        if (map == null)
            throw new NullPointerException("map");
        this.map = map;
    }

    @Override
    public Set<String> keySet() {
        return map.keySet();
    }

    @Override
    public boolean containsKey(String key) {
        return map.containsKey(key);
    }

    @Override
    public Object get(String key) {
        Object value = map.get(key);

        if (value == null)
            return null;

        if (value.getClass().isArray()) {
            // if (Array.getLength(value) == 0)
            // return null;
            return Array.get(value, 0);
        }

        return value;
    }

    @Override
    public String[] getStringArray(String key) {
        return getStringArray(key, null);
    }

    @Override
    public String[] getStringArray(String key, String[] defaultValue) {
        Object value = map.get(key);

        if (value == null)
            return defaultValue;

        if (value.getClass().isArray())
            return (String[]) value;

        String[] array = { String.valueOf(value) };
        return array;
    }

    @Override
    public Date getDate(String key, Date defaultValue) {
        String s = getString(key);
        if (s == null)
            return defaultValue;

        try {
            return Dates.YYYY_MM_DD.parse(s);
        } catch (java.text.ParseException e) {
            return defaultValue;
        }
    }

    @Override
    public Date getDate(String key) {
        return getDate(key, null);
    }

}
