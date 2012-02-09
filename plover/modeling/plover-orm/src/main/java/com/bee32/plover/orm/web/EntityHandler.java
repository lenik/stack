package com.bee32.plover.orm.web;

import java.io.Serializable;

import javax.free.IllegalUsageException;
import javax.inject.Inject;
import javax.servlet.ServletException;

import org.springframework.dao.DataAccessException;

import com.bee32.plover.ajax.SuccessOrFailMessage;
import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.IEntityAccessService;
import com.bee32.plover.orm.util.IEntityMarshalContext;
import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.servlet.mvc.ActionHandler;
import com.bee32.plover.servlet.mvc.ActionRequest;
import com.bee32.plover.servlet.mvc.ActionResult;

/**
 * EntityHandler 可含有需注入的属性，但不能有事务方法，以及其它需要 AOP/Proxy 的方法。
 */
public abstract class EntityHandler<E extends Entity<K>, K extends Serializable>
        extends ActionHandler
        implements IEntityMarshalContext, ITypeAbbrAware {

    @Inject
    protected CommonDataManager dataManager;

    // EntityType
    protected EntityHelper<E, K> eh;

    public EntityHandler(Class<E> entityType) {
        // Class<E> entityType = ClassUtil.infer1(getClass(), EntityHandler.class, 0);
        if (entityType == null)
            throw new NullPointerException("entityType");

        setEntityType(entityType);
    }

    public void setEntityType(Class<? extends E> baseEntityType) {
        if (baseEntityType == null)
            throw new NullPointerException("baseEntityType");
        eh = new EntityHelper<E, K>(baseEntityType);
    }

    // public EntityHelper<E, K> getEntityHelper() {
    // return eh;
    // }

    /**
     * Return the persistent instance of the given entity class with the given identifier, throwing
     * an exception if not found.
     *
     * @param entityClass
     *            a persistent class
     * @param id
     *            the identifier of the persistent instance
     * @return the persistent instance
     * @throws org.springframework.orm.ObjectRetrievalFailureException
     *             if not found
     * @throws org.springframework.dao.DataAccessException
     *             in case of Hibernate errors
     */
    @Override
    public <_E extends Entity<_K>, _K extends Serializable> _E getOrFail(Class<_E> entityType, _K id)
            throws DataAccessException {
        return asFor(entityType).getOrFail(id);
    }

    @Override
    public <_E extends Entity<_K>, _K extends Serializable> _E getRef(Class<_E> entityType, _K id)
            throws DataAccessException {
        return asFor(entityType).lazyLoad(id);
    }

    @Override
    public <_E extends Entity<? extends _K>, _K extends Serializable> //
    IEntityAccessService<_E, _K> asFor(Class<? extends _E> entityType) {
        IEntityAccessService<_E, _K> service = dataManager.asFor(entityType);
        return service;
    }

    @Override
    public final ActionResult handleRequest(ActionRequest req, ActionResult result)
            throws Exception {

        String typeAbbr = null;

        String pathParam = req.getPathParameter();
        if (pathParam != null) {
            if (pathParam.startsWith("/"))
                pathParam = pathParam.substring(1);
            typeAbbr = pathParam;
        }

        if (typeAbbr != null && !typeAbbr.isEmpty()) {
            Class<? extends E> entityType;
            try {
                entityType = (Class<? extends E>) ABBR.expand(typeAbbr);
            } catch (ClassNotFoundException e) {
                throw new IllegalArgumentException("Qualified class name, but not existed " + typeAbbr, e);
            }

            if (entityType == null)
                throw new IllegalUsageException("Bad entity abbrev: " + typeAbbr);

            if (!Entity.class.isAssignableFrom(entityType))
                throw new IllegalUsageException("Not subclass of entity: " + entityType);

            // XXX Thread-safe?
            this.setEntityType(entityType);
        }

        return _handleRequest(req, result);
    }

    /**
     * @see #handleRequestSOF(ActionRequest, ActionResult)
     */
    protected abstract ActionResult _handleRequest(ActionRequest req, ActionResult result)
            throws Exception;

    String successMessage = "Success";

    protected String getSuccessMessage() {
        return successMessage;
    }

    protected void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    protected final ActionResult handleRequestSOF(ActionRequest req, ActionResult result)
            throws Exception {
        SuccessOrFailMessage sof = new SuccessOrFailMessage(getSuccessMessage()) {
            @Override
            protected String eval()
                    throws Exception {
                return EntityHandler.this.eval();
            }
        };
        return sof.jsonDump(result);
    }

    /**
     * Only called if {@link #handleRequestSOF(EntityActionRequest, EntityActionResult)} is used.
     *
     * @return <code>null</code> if successful, otherwise error message.
     */
    protected String eval()
            throws ServletException {
        return "Not implemented.";
    }

    public static final NotApplicableHandler NOT_APPLICABLE = new NotApplicableHandler();

}
