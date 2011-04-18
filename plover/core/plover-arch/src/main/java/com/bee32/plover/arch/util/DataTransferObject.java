package com.bee32.plover.arch.util;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.free.AbstractVariantLookupMap;
import javax.free.IVariantLookupMap;
import javax.free.IllegalUsageException;
import javax.free.Map2VariantLookupMap;
import javax.free.NotImplementedException;
import javax.free.ParseException;
import javax.free.TypeConvertException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public abstract class DataTransferObject<T>
        implements IDataTransferObject<T>, Serializable {

    private static final long serialVersionUID = 1L;

    protected final Class<T> dataType;
    {
        Type[] dtoArgs = ClassUtil.getTypeArgs(getClass(), DataTransferObject.class);
        dataType = ClassUtil.bound1(dtoArgs[0]);
    }

    protected final Flags32 selection;

    public DataTransferObject() {
        this(0);
    }

    public DataTransferObject(int selection) {
        this.selection = new Flags32(selection);
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

    private static ThreadLocal<Map<Object, Object>> threadLocalGraph;
    static {
        threadLocalGraph = new ThreadLocal<Map<Object, Object>>();
    }

    /**
     * Marshal the given source object into this object.
     *
     * @param source
     *            Source object to be marshalled, maybe <code>null</code>.
     * @return The marshalled object. it may be this, or flyweight, or <code>null</code> if the
     *         specified <code>source</code> object is <code>null</code>.
     */
    @Override
    public final <D extends DataTransferObject<T>> D marshal(T source) {
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

        try {
            // Do the real marshal work.
            // logger.debug("marshal begin");

            graph.put(source, this);
            __marshal(source);

            // logger.debug("marshal end");
        } finally {
            if (topLevel)
                threadLocalGraph.remove();
        }

        @SuppressWarnings("unchecked")
        D self = (D) this;

        return self;
    }

    protected void __marshal(T source) {
        _marshal(source);
    }

    /**
     * Read some properties from the specified source bean into this object.
     *
     * @param source
     *            Non-null source source whose properties are read into this object.
     */
    protected abstract void _marshal(T source);

    public static <D extends DataTransferObject<T>, T> T unmarshal(D dto) {
        if (dto == null)
            return null;
        else
            return dto.unmarshal();
    }

    /**
     * Write properties from this object into a new source bean.
     *
     * @return Non-<code>null</code> source bean.
     */
    @Override
    public final T unmarshal() {
        T target;
        try {
            target = dataType.newInstance();
        } catch (ReflectiveOperationException e) {
            throw new IllegalUsageException("Failed to instantiate source bean " + dataType.getName(), e);
        }
        unmarshalTo(target);
        return target;
    }

    @Override
    public void unmarshalTo(T target) {
        _unmarshalTo(target);
    }

    /**
     * Write some properties from this object into the specified source bean.
     *
     * @param target
     *            Non-<code>null</code> target source bean.
     * @throws NotImplementedException
     *             If unmarshal isn't supported for this DTO.
     */
    protected abstract void _unmarshalTo(T target);

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

    public static <D extends DataTransferObject<T>, T> D marshal(Class<D> dtoClass, T source) {
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

    /**
     * Marshal each source to a DTO object.
     *
     * @param dtoClass
     *            The DTO type to be marshalled to.
     * @param values
     *            Entity array to be marshalled.
     * @return <code>null</code>.
     */
    public static <D extends DataTransferObject<T>, T> List<D> marshalList(Class<D> dtoClass, T... values) {
        List<T> sourceList = Arrays.asList(values);
        return marshalList(dtoClass, null, sourceList);
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
    public static <D extends DataTransferObject<T>, T> List<D> marshalList(Class<D> dtoClass, int selection,
            T... values) {
        List<T> sourceList = Arrays.asList(values);
        return marshalList(dtoClass, selection, sourceList);
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
    public static <D extends DataTransferObject<T>, T> List<D> marshalList(Class<D> dtoClass, Iterable<?> values) {
        return marshalList(dtoClass, null, values);
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
    public static <D extends DataTransferObject<T>, T> List<D> marshalList(Class<D> dtoClass, Integer selection,
            Iterable<?> values) {

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
                        dto.selection.set(selection);

                    dto.marshal(source);
                }
                dtoList.add(dto);
            }
        } catch (ReflectiveOperationException e) {
            throw new IllegalUsageException("Failed to instantiate " + dtoClass.getName(), e);
        }

        return dtoList;
    }

    public static <C extends Collection<T>, D extends DataTransferObject<T>, T> //
    C unmarshal(C collection, Iterable<? extends DataTransferObject<T>> dtoList) {

        if (dtoList == null)
            return null;

        for (DataTransferObject<T> dto : dtoList) {
            T source;
            if (dto == null)
                source = null;
            else
                source = dto.unmarshal();
            collection.add(source);
        }

        return collection;
    }

    public static <D extends DataTransferObject<T>, T> List<T> unmarshalList(
            Iterable<? extends DataTransferObject<T>> dtoList) {
        return unmarshal(new ArrayList<T>(), dtoList);
    }

    public static <D extends DataTransferObject<T>, T> Set<T> unmarshalSet(
            Iterable<? extends DataTransferObject<T>> dtoList) {
        return unmarshal(new HashSet<T>(), dtoList);
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
    public Set<String> keySet() {
        return map.keySet();
    }

}
