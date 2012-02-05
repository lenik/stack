package com.bee32.plover.orm.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.free.IllegalUsageException;
import javax.free.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.arch.util.ISelection;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.web.EntityHelper;
import com.bee32.plover.restful.resource.StandardViews;
import com.bee32.sem.frame.ui.IEnclosingContext;
import com.bee32.sem.misc.SimpleEntityViewBean;

public abstract class EntityViewBean
        extends DataViewBean
        implements ISelection, IEnclosingContext {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(EntityViewBean.class);

    List<?> selection = new ArrayList<Object>();
    List<?> openedObjects = new ArrayList<Object>();

    public EntityViewBean() {
        if (logger.isTraceEnabled()) {
            Class<?> viewBeanType = getClass();
            logger.trace("Entity view bean created: " + viewBeanType);
        }
    }

    @Override
    protected Class<? extends Entity<?>> getMajorTypeOfContextPage() {
        Class<?> majorType = super.getMajorTypeOfContextPage();
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
    protected <E extends Entity<K>, K extends Serializable> Serializable getRequestIdOfContextPage() {
        @SuppressWarnings("unchecked")
        Class<E> entityType = (Class<E>) getMajorTypeOfContextPage();

        String requestId = getRequest().getParameter(StandardViews.ID_PARAM);
        if (requestId == null)
            return null;

        EntityHelper<E, K> eh = EntityHelper.getInstance(entityType);
        Serializable id;
        try {
            id = eh.parseId(requestId);
            return id;
        } catch (ParseException e) {
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
     * @deprecated
     * @see SimpleEntityViewBean#requestWindow
     */
    @Deprecated
    protected Entity<?> getRequestEntity(boolean mustExist) {
        Class<? extends Entity<?>> entityType = getMajorTypeOfContextPage();
        Serializable id = getRequestIdOfContextPage();
        if (id == null)
            throw new IllegalArgumentException(String.format("Id for %s is not specified.", //
                    ClassUtil.getTypeName(entityType)));

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

    public final List<?> getSelection(Class<?>... interfaces) {
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

    public final Object getSingleSelection() {
        List<?> selection = getSelection();
        if (selection.isEmpty())
            return null;
        else
            return selection.get(0);
    }

    public final void setSingleSelection(Object singleSelection) {
        List<Object> list = new ArrayList<Object>();
        if (singleSelection != null)
            list.add(singleSelection);
        setSelection(list);
    }

    public final Object[] getSelectionArray() {
        Object[] array = selection.toArray();
        return array;
    }

    public final void setSelectionArray(Object... selectionArray) {
        List<Object> list = new ArrayList<Object>(selectionArray.length);
        for (Object item : selectionArray)
            list.add(item);
        setSelection(list);
    }

    public final boolean isSelected() {
        return !getSelection().isEmpty();
    }

    @Override
    public Object getEnclosingObject() {
        return getOpenedObject();
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getOpenedObjects() {
        return ((List<T>) openedObjects);
    }

    public void setOpenedObjects(List<?> openedObjects) {
        if (openedObjects == null)
            openedObjects = Collections.emptyList();
        this.openedObjects = openedObjects;
    }

    public final <T> T getOpenedObject(boolean selectionRequired) {
        T openedObject = getOpenedObject();
        if (!selectionRequired && openedObject == null) {
            uiLogger.error("请先选择对象。");
            return null;
        }
        return openedObject;
    }

    public final <T> T getOpenedObject() {
        List<?> objects = getOpenedObjects();
        if (objects.isEmpty())
            return null;
        T first = (T) objects.get(0);
        return first;
    }

    public final void setOpenedObject(Object openedObject) {
        List<?> nonNulls = listOfNonNulls(openedObject);
        setOpenedObjects(nonNulls);
    }

    protected void openSelection() {
        int fmask = -1;
        String fmaskParam = getRequest().getParameter("fmask");
        if (fmaskParam != null)
            fmask = Integer.parseInt(fmaskParam);
        openSelection(fmask);
    }

    protected void openSelection(int fmask) {
        List<Object> reloadedList = new ArrayList<Object>();
        for (Object selection : getSelection()) {
            Object reloaded;
            if (selection instanceof EntityDto<?, ?>) {
                EntityDto<?, ?> dto = (EntityDto<?, ?>) selection;
                reloaded = reload(dto, fmask);
            } else
                reloaded = selection;
            reloadedList.add(reloaded);
        }
        setOpenedObjects(reloadedList);
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
