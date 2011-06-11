package com.bee32.plover.orm.ext.basic;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import javax.free.ParseException;
import javax.servlet.ServletException;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityDao;
import com.bee32.plover.orm.entity.EntityDso;
import com.bee32.plover.orm.entity.EntityUtil;
import com.bee32.plover.orm.util.EntityDto;

public class EntityHelper<E extends Entity<K>, K extends Serializable> {

    Class<E> entityType;
    Class<K> keyType;
    Class<? extends EntityDto<E, K>> dtoType;
    Class<? extends EntityDao<E, K>> daoType;
    Class<? extends EntityDso<E, K>> dsoType;

    Map<SelectionMode, Integer> selectionModes = new HashMap<SelectionMode, Integer>();

    public EntityHelper(Class<? extends E> entityType) {
        if (entityType == null)
            throw new NullPointerException("entityType");

        @SuppressWarnings("unchecked")
        Class<E> _entityType = (Class<E>) entityType;

        this.entityType = _entityType;

        keyType = EntityUtil.getKeyType(entityType);

        daoType = (Class<? extends EntityDao<E, K>>) EntityUtil.getDaoType(entityType);
        dtoType = (Class<? extends EntityDto<E, K>>) EntityUtil.getDtoType(entityType);
    }

    public Class<? extends E> getEntityType() {
        return entityType;
    }

    public Class<? extends K> getKeyType() {
        return keyType;
    }

    public Class<? extends EntityDao<E, K>> getDaoType() {
        return daoType;
    }

    public void setDaoType(Class<? extends EntityDao<E, K>> daoType) {
        if (daoType == null)
            throw new NullPointerException("daoType");
        this.daoType = daoType;
    }

    public Class<? extends EntityDto<E, K>> getDtoType() {
        return dtoType;
    }

    public void setDtoType(Class<? extends EntityDto<E, K>> dtoType) {
        if (dtoType == null)
            throw new NullPointerException("dtoType");
        this.dtoType = dtoType;
    }

    public E newEntity()
            throws ServletException {
        try {
            return entityType.newInstance();
        } catch (ReflectiveOperationException e) {
            throw new ServletException(e.getMessage(), e);
        }
    }

    public EntityDto<E, K> newDto()
            throws ServletException {
        return newDto((Integer) null);
    }

    public EntityDto<E, K> newDto(Integer selection)
            throws ServletException {

        EntityDto<E, K> dto;

        try {
            if (selection == null) {
                dto = dtoType.newInstance();
            } else {
                Constructor<? extends EntityDto<E, K>> selectionCtor = dtoType.getConstructor(int.class);
                dto = selectionCtor.newInstance(selection.intValue());
            }
        } catch (ReflectiveOperationException e) {
            throw new ServletException("Failed to create DTO of " + dtoType, e);
        }

        dto.initSourceType(entityType);
        return dto;
    }

    public EntityDto<E, K> newDto(SelectionMode mode)
            throws ServletException {
        Integer selection = selectionModes.get(mode);
        return newDto(selection);
    }

    public Integer getSelection(SelectionMode mode) {
        return selectionModes.get(mode);
    }

    public void setSelection(SelectionMode mode, Integer selection) {
        if (mode == null)
            throw new NullPointerException("mode");
        selectionModes.put(mode, selection);
    }

    /**
     * Parse the id string in the format of corresponding key type.
     *
     * @return <code>null</code> If the given id string is <code>null</code>.
     */
    public K parseId(String idString)
            throws ServletException {
        K id;
        try {
            id = EntityUtil.parseId(keyType, idString);
        } catch (ParseException e) {
            throw new ServletException("无效的标识：" + idString, e);
        }
        return id;
    }

    public K parseRequiredId(String idString)
            throws ServletException {
        if (idString == null)
            throw new ServletException("没有指定标识。");
        return parseId(idString);
    }

    public String getHint(K id) {
        String entityTypeName = ClassUtil.getDisplayName(entityType);
        return entityTypeName + " [" + id + "]";
    }

    public String getHint(E entity) {
        return ClassUtil.getDisplayName(entity.getClass()) + " [" + entity.getId() + "]";
    }

}
