package com.bee32.plover.orm.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.free.IllegalUsageException;
import javax.free.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.web.EntityHelper;
import com.bee32.plover.restful.resource.StandardViews;
import com.bee32.sem.frame.ui.IEnclosingContext;
import com.bee32.sem.misc.SimpleEntityViewBean;

public abstract class EntityViewBean
        extends DataViewBean
        implements IEnclosingContext {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(EntityViewBean.class);

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

        String requestId = ctx.getRequest().getParameter(StandardViews.ID_PARAM);
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
    public Object getEnclosingObject() {
        return getOpenedObject();
    }

    protected void openSelection() {
        int fmask = -1;
        String fmaskParam = ctx.getRequest().getParameter("fmask");
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

}
