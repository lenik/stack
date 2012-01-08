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
import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.IEntityAccessService;
import com.bee32.plover.orm.web.EntityHelper;
import com.bee32.plover.restful.resource.StandardViews;
import com.bee32.plover.web.faces.view.GenericViewBean;

public abstract class EntityViewBean
        extends GenericViewBean
        implements IEntityMarshalContext {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(EntityViewBean.class);

    public EntityViewBean() {
        if (logger.isTraceEnabled()) {
            Class<?> viewBeanType = getClass();
            logger.trace("Entity view bean created: " + viewBeanType);
        }
    }

    static CommonDataManager getDataManager() {
        CommonDataManager dataManager = getBean(CommonDataManager.class);
        return dataManager;
    }

    protected static <E extends Entity<? extends K>, K extends Serializable> //
    IEntityAccessService<E, K> serviceFor(Class<? extends E> entityType) {
        IEntityAccessService<E, K> service = getDataManager().asFor(entityType);
        return service;
    }

    @Override
    public <E extends Entity<? extends K>, K extends Serializable> //
    IEntityAccessService<E, K> asFor(Class<? extends E> entityType) {
        IEntityAccessService<E, K> service = getDataManager().asFor(entityType);
        return service;
    }

    @Override
    public <E extends Entity<K>, K extends Serializable> //
    E loadEntity(Class<E> entityType, K id) {
        E entity = serviceFor(entityType).getOrFail(id);
        return entity;
    }

    protected static <E extends Entity<K>, K extends Serializable> //
    E reloadEntity(E entity) {
        Class<? extends E> entityType = (Class<? extends E>) entity.getClass();
        K id = entity.getId();

        E reloaded = serviceFor(entityType).getOrFail(id);

        return reloaded;
    }

    protected static <D extends EntityDto<E, K>, E extends Entity<K>, K extends Serializable> //
    D reload(D dto) {
        return reload(dto, dto.getSelection());
    }

    protected static <D extends EntityDto<E, K>, E extends Entity<K>, K extends Serializable> //
    D reload(D dto, int selection) {
        Class<? extends D> dtoType = (Class<? extends D>) dto.getClass();
        Class<? extends E> entityType = dto.getEntityType();
        K id = dto.getId();

        E reloaded = serviceFor(entityType).getOrFail(id);

        D remarshalled = DTOs.marshal(dtoType, selection, reloaded);
        return remarshalled;
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

    public List<?> getSelection() {
        return Collections.emptyList();
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
