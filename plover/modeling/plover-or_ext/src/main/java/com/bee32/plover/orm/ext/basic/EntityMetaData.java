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

public class EntityMetaData {

    Class<? extends Entity<?>> entityType;
    Class<? extends Serializable> keyType;
    Class<? extends EntityDto<Entity<?>, ?>> dtoType;
    Class<? extends EntityDao<Entity<?>, ?>> daoType;
    Class<? extends EntityDso<Entity<?>, ?>> dsoType;

    Map<SelectionMode, Integer> selectionModes = new HashMap<SelectionMode, Integer>();

    public EntityMetaData(Class<? extends Entity<?>> entityType) {
        if (entityType == null)
            throw new NullPointerException("entityType");
        this.entityType = entityType;

        keyType = EntityUtil.getKeyType(entityType);

        daoType = (Class<? extends EntityDao<Entity<?>, ?>>) EntityUtil.getDaoType(entityType);
        dtoType = (Class<? extends EntityDto<Entity<?>, ?>>) EntityUtil.getDtoType(entityType);
    }

    public Class<? extends Entity<?>> getEntityType() {
        return entityType;
    }

    public Class<? extends Serializable> getKeyType() {
        return keyType;
    }

    public Class<? extends EntityDao<Entity<?>, ?>> getDaoType() {
        return daoType;
    }

    public void setDaoType(Class<? extends EntityDao<Entity<?>, ?>> daoType) {
        if (daoType == null)
            throw new NullPointerException("daoType");
        this.daoType = daoType;
    }

    public Class<? extends EntityDto<Entity<?>, ?>> getDtoType() {
        return dtoType;
    }

    public void setDtoType(Class<? extends EntityDto<Entity<?>, ?>> dtoType) {
        if (dtoType == null)
            throw new NullPointerException("dtoType");
        this.dtoType = dtoType;
    }

    public Entity<?> newEntity()
            throws ServletException {
        try {
            return entityType.newInstance();
        } catch (ReflectiveOperationException e) {
            throw new ServletException(e.getMessage(), e);
        }
    }

    public EntityDto<?, ?> newDto()
            throws ServletException {
        return newDto((Integer) null);
    }

    public EntityDto<?, ?> newDto(Integer selection)
            throws ServletException {

        EntityDto<Entity<?>, ?> dto;

        try {
            if (selection == null) {
                dto = dtoType.newInstance();
            } else {
                Constructor<? extends EntityDto<Entity<?>, ?>> selectionCtor = dtoType.getConstructor(int.class);
                dto = selectionCtor.newInstance(selection.intValue());
            }
        } catch (ReflectiveOperationException e) {
            throw new ServletException("Failed to create DTO of " + dtoType, e);
        }

        dto.initSourceType(entityType);
        return dto;
    }

    public EntityDto<?, ?> newDto(SelectionMode mode)
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
    public Serializable parseId(String idString)
            throws ServletException {
        Serializable id;
        try {
            id = EntityUtil.parseId(keyType, idString);
        } catch (ParseException e) {
            throw new ServletException("无效的标识：" + idString, e);
        }
        return id;
    }

    public Serializable parseRequiredId(String idString)
            throws ServletException {
        if (idString == null)
            throw new ServletException("没有指定标识。");
        return parseId(idString);
    }

    public String getHint(Serializable id) {
        String entityTypeName = ClassUtil.getDisplayName(entityType);
        return entityTypeName + " [" + id + "]";
    }

    public String getHint(Entity<?> entity) {
        return ClassUtil.getDisplayName(entity.getClass()) + " [" + entity.getId() + "]";
    }

}
