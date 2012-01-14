package com.bee32.plover.orm.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.free.IllegalUsageException;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.arch.util.ISelection;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.web.EntityHelper;
import com.bee32.plover.restful.resource.StandardViews;

public abstract class EntityViewBean
        extends DataViewBean
        implements ISelection {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(EntityViewBean.class);

    List<?> selection = new ArrayList<Object>();
    List<?> activeObjects = new ArrayList<Object>();

    public EntityViewBean() {
        if (logger.isTraceEnabled()) {
            Class<?> viewBeanType = getClass();
            logger.trace("Entity view bean created: " + viewBeanType);
        }
    }

    @Override
    protected Class<? extends Entity<?>> getMajorType() {
        Class<?> majorType = super.getMajorType();
        if (!Entity.class.isAssignableFrom(majorType))
            throw new IllegalUsageException("The major type should be Entity for entity view bean: " + majorType);

        @SuppressWarnings("unchecked")
        Class<? extends Entity<?>> entityType = (Class<? extends Entity<?>>) majorType;

        return entityType;
    }

    /**
     * Get and parse the request entity id.
     *
     * @return <code>null</code> If id parameter is not specified in the rqeuest.
     */
    protected <E extends Entity<K>, K extends Serializable> Serializable getRequestId() {
        @SuppressWarnings("unchecked")
        Class<E> entityType = (Class<E>) getMajorType();

        String requestId = getRequest().getParameter(StandardViews.ID_PARAM);
        if (requestId == null)
            return null;

        EntityHelper<E, K> eh = EntityHelper.getInstance(entityType);
        Serializable id;
        try {
            id = eh.parseId(requestId);
            return id;
        } catch (ServletException e) {
            throw new IllegalArgumentException("Bad id: " + requestId, e);
        }
    }

    /**
     * Get the actual entity for the request.
     *
     * @param mustExist
     *            Whether the entity have to be existed.
     * @return While <code>mustExist</code> is <code>false</code>, returns <code>null</code> if not
     *         found. Otherwise non-<code>null</code> values.
     * @throws IllegalArgumentException
     *             If request id is not specified, or could not parsed.
     * @throws org.springframework.orm.ObjectRetrievalFailureException
     *             if not found but <code>mustExist</code> was specified to <code>true</code>.
     * @throws org.springframework.dao.DataAccessException
     *             in case of Hibernate errors
     */
    protected Entity<?> getRequestEntity(boolean mustExist) {
        Serializable id = getRequestId();
        if (id == null)
            throw new IllegalArgumentException(String.format("Id for %s is not specified.", //
                    ClassUtil.getTypeName(getMajorType())));

        Class<? extends Entity<?>> entityType = getMajorType();
        Entity<?> entity;
        if (mustExist)
            entity = asFor(entityType).getOrFail(id);
        else
            entity = asFor(entityType).get(id);
        return entity;
    }

    @Override
    public List<?> getSelection() {
        return selection;
    }

    public List<?> getSelection(Class<?>... interfaces) {
        List<?> selection = getSelection();
        if (interfaces.length == 0)
            return selection;
        List<Object> interestings = new ArrayList<Object>();
        for (Object item : selection) {
            boolean interesting = true;
            if (item == null)
                continue;
            for (Class<?> iface : interfaces)
                if (!iface.isInstance(item)) {
                    interesting = false;
                    break;
                }
            if (interesting)
                interestings.add(item);
        }
        return interestings;
    }

    public void setSelection(List<?> selection) {
        if (selection == null)
            selection = new ArrayList<Object>();
        this.selection = selection;
    }

    public Object getSingleSelection() {
        List<?> selection = getSelection();
        if (selection.isEmpty())
            return null;
        else
            return selection.get(0);
    }

    public void setSingleSelection(Object singleSelection) {
        List<Object> list = new ArrayList<Object>();
        if (singleSelection != null)
            list.add(singleSelection);
        setSelection(list);
    }

    public Object[] getSelectionArray() {
        Object[] array = selection.toArray();
        return array;
    }

    public void setSelectionArray(Object... selectionArray) {
        List<Object> list = new ArrayList<Object>(selectionArray.length);
        for (Object item : selectionArray)
            list.add(item);
        setSelection(list);
    }

    public boolean isSelected() {
        return !getSelection().isEmpty();
    }

    public List<?> getActiveObjects() {
        return activeObjects;
    }

    public void setActiveObjects(List<?> activeObjects) {
        if (activeObjects == null)
            activeObjects = Collections.emptyList();
        this.activeObjects = activeObjects;
    }

    public <T> T getActiveObject(boolean selectionRequired) {
        T activeObject = getActiveObject();
        if (!selectionRequired && activeObject == null) {
            uiLogger.error("请先选择对象。");
            return null;
        }
        return activeObject;
    }

    public <T> T getActiveObject() {
        List<?> objects = getActiveObjects();
        if (objects.isEmpty())
            return null;
        T first = (T) objects.get(0);
        return first;
    }

    public void setActiveObject(Object activeObject) {
        List<?> nonNulls = listOfNonNulls(activeObject);
        setActiveObjects(nonNulls);
    }

    public boolean isActived() {
        return !getActiveObjects().isEmpty();
    }

    @SafeVarargs
    protected static <T> List<T> listOf(T... selection) {
        return Arrays.asList(selection);
    }

    @SafeVarargs
    protected static <T> List<T> listOfNonNulls(T... selection) {
        List<T> list = new ArrayList<T>(selection.length);
        for (T item : selection)
            if (item != null)
                list.add(item);
        return Collections.unmodifiableList(list);
    }

}
