package com.bee32.plover.orm.ext.basic;

import java.io.Serializable;

import javax.inject.Inject;
import javax.servlet.ServletException;

import org.springframework.dao.DataAccessException;

import com.bee32.plover.ajax.SuccessOrFailMessage;
import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.IEntityMarshalContext;
import com.bee32.plover.servlet.mvc.ActionHandler;
import com.bee32.plover.servlet.mvc.ActionRequest;
import com.bee32.plover.servlet.mvc.ActionResult;

/**
 * EntityHandler 可含有需注入的属性，但不能有事务方法，以及其它需要 AOP/Proxy 的方法。
 */
public abstract class EntityHandler<E extends Entity<K>, K extends Serializable>
        extends ActionHandler
        implements IEntityMarshalContext {

    @Inject
    protected CommonDataManager dataManager;

    // EntityType
    protected EntityHelper<E, K> eh;

    public EntityHandler() {
    }

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
    public <_E extends Entity<_K>, _K extends Serializable> _E loadEntity(Class<_E> entityType, _K id)
            throws DataAccessException {
        return dataManager.fetch(entityType, id);
    }

    @Override
    public ActionResult handleRequest(ActionRequest req, ActionResult result)
            throws Exception {

        return null;
    }

    /**
     * @see #handleRequestSOF(EntityActionRequest, EntityActionResult)
     */
    public abstract ActionResult handleRequest(EntityActionRequest req, EntityActionResult result)
            throws Exception;

    String successMessage = "Success";

    protected String getSuccessMessage() {
        return successMessage;
    }

    protected void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    protected final ActionResult handleRequestSOF(EntityActionRequest req, EntityActionResult result)
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

}
