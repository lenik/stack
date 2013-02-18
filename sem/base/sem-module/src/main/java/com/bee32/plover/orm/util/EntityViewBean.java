package com.bee32.plover.orm.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.free.IllegalUsageException;
import javax.free.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.arch.util.IEnclosingContext;
import com.bee32.plover.arch.util.dto.BaseDto;
import com.bee32.plover.arch.util.dto.Fmask;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityUtil;
import com.bee32.plover.orm.web.EntityHelper;
import com.bee32.plover.restful.resource.StandardViews;
import com.bee32.sem.misc.SimpleEntityViewBean;

public abstract class EntityViewBean
        extends DataViewBean
        implements IEnclosingContext {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(EntityViewBean.class);

    private Class<? extends Entity<?>> entityClass;
    private Class<? extends EntityDto<?, ?>> dtoClass;
    private Class<? extends Serializable> keyClass;

    public EntityViewBean() {
        if (logger.isTraceEnabled()) {
            Class<?> viewBeanType = getClass();
            logger.trace("Entity view bean created: " + viewBeanType);
        }
    }

    /**
     * Type-safe accessor wrapper.
     */
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

        String requestId = ctx.view.getRequest().getParameter(StandardViews.ID_PARAM);
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
            entity = DATA(entityType).getOrFail(id);
        else
            entity = DATA(entityType).get(id);
        return entity;
    }

    @Override
    public Object getEnclosingObject() {
        return getOpenedObject();
    }

    @Override
    public boolean isSelectionEditable() {
        if (getSelection().isEmpty())
            return false;
        for (Object sel : getSelection()) {
            if (sel instanceof BaseDto<?>) {
                BaseDto<?> dto = (BaseDto<?>) sel;
                if (dto.isLocked())
                    return false;
            }
        }
        return true;
    }

    @Override
    public void openSelection() {
        int fmask = Fmask.F_MORE;
        String fmaskParam = ctx.view.getRequest().getParameter("fmask");
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

    protected Class<? extends Entity<?>> getEntityType() {
        return entityClass;
    }

    protected Class<? extends EntityDto<?, ?>> getEntityDtoType() {
        return dtoClass;
    }

    protected Class<? extends Serializable> getKeyType() {
        return keyClass;
    }

    protected void setEntityType(Class<? extends Entity<?>> entityType) {
        this.entityClass = entityType;
        this.keyClass = EntityUtil.getKeyType(entityClass);
    }

    public void setEntityDtoType(Class<? extends EntityDto<?, ?>> dtoClass) {
        this.dtoClass = dtoClass;
    }

    public String getEntityTypeAbbr() {
        return ITypeAbbrAware.ABBR.abbr(entityClass);
    }

}
