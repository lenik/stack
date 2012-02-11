package com.bee32.plover.orm.web;

import java.io.Serializable;

import javax.free.IllegalUsageException;
import javax.servlet.ServletException;

import com.bee32.plover.ajax.SuccessOrFailMessage;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.DefaultDataAssembledContext;
import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.servlet.mvc.ActionHandler;
import com.bee32.plover.servlet.mvc.ActionRequest;
import com.bee32.plover.servlet.mvc.ActionResult;

/**
 * EntityHandler 可含有需注入的属性，但不能有事务方法，以及其它需要 AOP/Proxy 的方法。
 */
public abstract class EntityHandler<E extends Entity<K>, K extends Serializable>
        extends ActionHandler
        implements ITypeAbbrAware {

    protected static class ctx
            extends DefaultDataAssembledContext {
    }

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
