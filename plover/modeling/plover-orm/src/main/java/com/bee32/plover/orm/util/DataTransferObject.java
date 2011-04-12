package com.bee32.plover.orm.util;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.free.IllegalUsageException;
import javax.servlet.http.HttpServletRequest;

import com.bee32.plover.orm.entity.EntityAccessor;
import com.bee32.plover.orm.entity.EntityBean;

public abstract class DataTransferObject<E extends EntityBean<K>, K extends Serializable>
        implements Serializable {

    private static final long serialVersionUID = 1L;

    private Class<E> entityClass;

    private K id;
    private Integer version;

    @SuppressWarnings("unchecked")
    public DataTransferObject() {
        ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
        entityClass = (Class<E>) superclass.getActualTypeArguments()[0];
    }

    public DataTransferObject(E entity) {
        this();

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

    public void clearId() {
        this.id = null;
        this.version = null;
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
        return marshalList(dtoClass, entityList);
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
    @SuppressWarnings("unchecked")
    public static <D extends DataTransferObject<Ed, Kd>, Ed extends EntityBean<Kd>, Kd extends Serializable> //
    // List<D> marshalList(Class<D> dtoClass, Iterable<? extends Ed> entities) {
    /*    */List<D> marshalList(Class<D> dtoClass, Iterable<?> entities) {

        if (entities == null)
            return null;

        List<D> dtoList = new ArrayList<D>();

        try {
            for (Object entity : entities) {
                D dto;
                if (entity == null)
                    dto = null;
                else {
                    dto = dtoClass.newInstance();
                    dto.marshal((Ed) entity);
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
