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
import com.bee32.plover.orm.entity.EntityAccessor;
import com.bee32.plover.orm.entity.EntityBean;

public abstract class DataTransferObject<E extends EntityBean<K>, K extends Serializable>
        implements Serializable {

    private static final long serialVersionUID = 1L;

    private Class<E> entityClass;

    protected final Flags32 selection;

    protected K id;
    protected Integer version;

    public DataTransferObject() {
        this(0);
    }

    @SuppressWarnings("unchecked")
    public DataTransferObject(int selection) {
        Type[] pv = ClassUtil.getOriginPV(getClass());
        entityClass = (Class<E>) pv[0];

        this.selection = new Flags32(selection);
    }

    public DataTransferObject(E entity) {
        this();

        if (entity == null)
            throw new NullPointerException("entity");

        marshal(entity);
    }

    public DataTransferObject(E entity, int selection) {
        this(selection);

        if (entity == null)
            throw new NullPointerException("entity");

        marshal(entity);
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

    public DataTransferObject<E, K> clearId() {
        this.id = null;
        this.version = null;
        return this;
    }

    protected <D extends DataTransferObject<E, K>> D birth() {
        Class<D> selfType = (Class<D>) getClass();

        D vcopy;
        try {
            vcopy = selfType.newInstance();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        vcopy.selection.set(selection);
        return vcopy;
    }

    public void marshal(E entity) {
        if (entity == null)
            throw new NullPointerException("entity");

        this.id = entity.getId();
        this.version = entity.getVersion();

        marshalNonNull(entity);
    }

    /**
     * Read some properties from the specified entity bean into this object.
     *
     * @param entity
     *            Non-null source entity whose properties are read into this object.
     */
    protected abstract void marshalNonNull(E entity);

    /**
     * Write properties from this object into a new entity bean.
     *
     * @return Non-<code>null</code> entity bean.
     */
    public final E unmarshal() {
        E entity;
        try {
            entity = entityClass.newInstance();
        } catch (ReflectiveOperationException e) {
            throw new IllegalUsageException("Failed to instantiate entity bean " + entityClass.getName(), e);
        }
        unmarshalTo(entity);
        return entity;
    }

    public final void unmarshalTo(E entity) {
        if (entity == null)
            throw new NullPointerException("entity");

        EntityAccessor.setId(entity, id);
        if (version != null)
            EntityAccessor.setVersion(entity, version);

        unmarshalNonNull(entity);
    }

    /**
     * Write some properties from this object into the specified entity bean.
     *
     * @param entity
     *            Non-<code>null</code> target entity bean.
     * @throws NotImplementedException
     *             If unmarshal isn't supported for this DTO.
     */
    protected abstract void unmarshalNonNull(E entity);

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

    public static <D extends DataTransferObject<Ed, Kd>, Ed extends EntityBean<Kd>, Kd extends Serializable> //
    /*    */D marshal(Class<D> dtoClass, Ed entity) {
        if (entity == null)
            return null;

        try {
            D dto = dtoClass.newInstance();
            dto.marshal(entity);
            return dto;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to instantiate DTO " + dtoClass.getName(), e);
        }
    }

    /**
     * Marshal each entity to a DTO object.
     *
     * @param dtoClass
     *            The DTO type to be marshalled to.
     * @param entities
     *            Entity array to be marshalled.
     * @return <code>null</code>.
     */
    public static <D extends DataTransferObject<Ed, Kd>, Ed extends EntityBean<Kd>, Kd extends Serializable> //
    /*    */List<D> marshalList(Class<D> dtoClass, Ed... entities) {
        List<Ed> entityList = Arrays.asList(entities);
        return marshalList(dtoClass, null, entityList);
    }

    /**
     * Marshal each entity to a DTO object.
     *
     * @param dtoClass
     *            The DTO type to be marshalled to.
     * @param selection
     *            Selection for each entity.
     * @param entities
     *            Entity array to be marshalled.
     * @return <code>null</code>.
     */
    public static <D extends DataTransferObject<Ed, Kd>, Ed extends EntityBean<Kd>, Kd extends Serializable> //
    /*    */List<D> marshalList(Class<D> dtoClass, int selection, Ed... entities) {
        List<Ed> entityList = Arrays.asList(entities);
        return marshalList(dtoClass, selection, entityList);
    }

    /**
     * Marshal each entity to a DTO object.
     *
     * @param dtoClass
     *            The DTO type to be marshalled to.
     * @param entities
     *            Entities to be marshalled.
     * @return <code>null</code>.
     */
    public static <D extends DataTransferObject<Ed, Kd>, Ed extends EntityBean<Kd>, Kd extends Serializable> //
    /*    */List<D> marshalList(Class<D> dtoClass, Iterable<?> entities) {
        return marshalList(dtoClass, null, entities);
    }

    /**
     * Marshal each entity to a DTO object.
     *
     * @param dtoClass
     *            The DTO type to be marshalled to.
     * @param selection
     *            Selection for each entity.
     * @param entities
     *            Entities to be marshalled.
     * @return <code>null</code>.
     */
    public static <D extends DataTransferObject<Ed, Kd>, Ed extends EntityBean<Kd>, Kd extends Serializable> //
    /*    */List<D> marshalList(Class<D> dtoClass, Integer selection, Iterable<?> entities) {

        if (entities == null)
            return null;

        List<D> dtoList = new ArrayList<D>();

        try {
            for (Object _entity : entities) {
                D dto;
                if (_entity == null)
                    dto = null;
                else {
                    @SuppressWarnings("unchecked")
                    Ed entity = (Ed) _entity;

                    dto = dtoClass.newInstance();
                    if (selection != null)
                        dto.selection.set(selection);

                    dto.marshal(entity);
                }
                dtoList.add(dto);
            }
        } catch (ReflectiveOperationException e) {
            throw new IllegalUsageException("Failed to instantiate " + dtoClass.getName(), e);
        }

        return dtoList;
    }

    public static <D extends DataTransferObject<Ed, Kd>, Ed extends EntityBean<Kd>, Kd extends Serializable> //
    /*    */List<Ed> unmarshalList(Iterable<? extends DataTransferObject<Ed, Kd>> dataTransferObjects) {

        if (dataTransferObjects == null)
            return null;

        List<Ed> entities = new ArrayList<Ed>();

        for (DataTransferObject<Ed, Kd> dto : dataTransferObjects) {
            Ed entity;
            if (dto == null)
                entity = null;
            else
                entity = dto.unmarshal();
            entities.add(entity);
        }

        return entities;
    }

}
