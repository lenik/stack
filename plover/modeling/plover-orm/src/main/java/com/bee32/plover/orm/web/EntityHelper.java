package com.bee32.plover.orm.web;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import javax.free.ParseException;
import javax.servlet.ServletException;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityDao;
import com.bee32.plover.orm.entity.EntityUtil;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.util.FormatStyle;

public class EntityHelper<E extends Entity<K>, K extends Serializable> {

    Class<E> entityType;
    Class<K> keyType;
    Class<? extends EntityDto<E, K>> dtoType;
    Class<? extends EntityDao<E, K>> daoType;
    // Class<? extends EntityDso<E, K>> dsoType;

    Integer defaultSelection;
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
        if (selection == null)
            selection = defaultSelection;
        return newDto(selection);
    }

    /**
     * Get the DTO selection of specific mode.
     *
     * @param mode
     *            Specify <code>null</code> to get the default selection.
     * @return <code>null</code> if the selection is unspecified.
     */
    public Integer getSelection(SelectionMode mode) {
        return selectionModes.get(mode);
    }

    /**
     * Set the DTO selection for specific mode.
     *
     * @param mode
     *            Specify <code>null</code> to change the default selection.
     * @param selection
     *            <code>null</code> if the selection is unspecified.
     */
    public void setSelection(SelectionMode mode, Integer selection) {
        if (mode == null)
            defaultSelection = selection;
        else
            selectionModes.put(mode, selection);
    }

    /**
     * Get the default selection.
     */
    public Integer getSelection() {
        return defaultSelection;
    }

    /**
     *
     *
     * @param selection
     *            <code>null</code> if the selection is unspecified.
     */
    public void setSelection(Integer defaultSelection) {
        this.defaultSelection = defaultSelection;
    }

    /**
     * Parse the id string in the format of corresponding key type.
     *
     * @return <code>null</code> If the given id string is <code>null</code>.
     */
    public K parseId(String idString)
            throws ParseException {
        K id;
        id = EntityUtil.parseId(keyType, idString);
        return id;
    }

    public K parseRequiredId(String idString)
            throws ServletException {
        if (idString == null)
            throw new ServletException("没有指定标识。");
        try {
            return parseId(idString);
        } catch (ParseException e) {
            throw new ServletException(e.getMessage(), e);
        }
    }

    public String getHint(K id) {
        String entityTypeName = ClassUtil.getTypeName(entityType);
        return entityTypeName + " [" + id + "]";
    }

    public static String getHint(Entity<?> entity) {
        return ClassUtil.getParameterizedTypeName(entity) + " [" + entity.getId() + "]";
    }

    public static String getHint(Entity<?> entity, FormatStyle format) {
        StringBuilder buf = new StringBuilder();
        buf.append(ClassUtil.getParameterizedTypeName(entity));
        buf.append(' ');
        buf.append(entity.toString(format));
        String str = buf.toString();
        return str;
    }

    static final Map<Class<?>, EntityHelper<?, ?>> classmap;
    static {
        classmap = new HashMap<Class<?>, EntityHelper<?, ?>>();
    }

    public static <E extends Entity<K>, K extends Serializable> //
    EntityHelper<E, K> getInstance(Class<E> entityType) {
        EntityHelper<?, ?> entityHelper = classmap.get(entityType);
        if (entityHelper == null) {
            synchronized (EntityHelper.class) {
                entityHelper = classmap.get(entityType);
                if (entityHelper == null) {
                    entityHelper = new EntityHelper<E, K>(entityType);
                    classmap.put(entityType, entityHelper);
                }
            }
        }
        @SuppressWarnings("unchecked")
        EntityHelper<E, K> eh = (EntityHelper<E, K>) entityHelper;
        return eh;
    }

}
