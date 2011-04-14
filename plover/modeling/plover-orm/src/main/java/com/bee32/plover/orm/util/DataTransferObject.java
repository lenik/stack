package com.bee32.plover.orm.util;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.free.IllegalUsageException;
import javax.free.NotImplementedException;
import javax.servlet.http.HttpServletRequest;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.arch.util.Flags32;
import com.bee32.plover.orm.entity.EntityBean;

public abstract class DataTransferObject<T>
        implements Serializable {

    private static final long serialVersionUID = 1L;

    protected final Class<T> dataType;
    {
        Type[] pv = ClassUtil.getOriginPV(getClass());

        @SuppressWarnings("unchecked")
        Class<T> dataType = (Class<T>) pv[0];

        this.dataType = dataType;
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

    public void marshal(T source) {
        _marshal(source);
    }

    /**
     * Read some properties from the specified source bean into this object.
     *
     * @param source
     *            Non-null source source whose properties are read into this object.
     */
    protected abstract void _marshal(T source);

    /**
     * Write properties from this object into a new source bean.
     *
     * @return Non-<code>null</code> source bean.
     */
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
     */
    public void parse(HttpServletRequest request) {
        parse(request.getParameterMap());
    }

    /**
     * Parse parameters from a map, and put the parsed result into this object.
     *
     * @param request
     *            Non-<code>null</code> request object.
     */
    public void parse(Map<String, ?> request) {
    }

    /**
     * Export to a map.
     */
    public void export(Map<String, Object> map) {
    }

    public static <D extends DataTransferObject<T>, T extends EntityBean<Kd>, Kd extends Serializable> //
    /*    */D marshal(Class<D> dtoClass, T source) {
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
     * @return <code>null</code>.
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

    public static <D extends DataTransferObject<T>, T> List<T> unmarshalList(
            Iterable<? extends DataTransferObject<T>> dtoList) {

        if (dtoList == null)
            return null;

        List<T> values = new ArrayList<T>();

        for (DataTransferObject<T> dto : dtoList) {
            T source;
            if (dto == null)
                source = null;
            else
                source = dto.unmarshal();
            values.add(source);
        }

        return values;
    }

}
