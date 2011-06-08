package com.bee32.plover.orm.ext.basic;

import java.io.Serializable;

import javax.inject.Inject;

import org.springframework.dao.DataAccessException;

import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.ext.util.EntityController;
import com.bee32.plover.orm.util.IEntityMarshalContext;
import com.bee32.plover.servlet.mvc.ActionHandler;

/**
 * EntityHandler 可含有需注入的属性，但不能有事务方法，以及其它需要 AOP/Proxy 的方法。
 */
public abstract class EntityHandler
        extends ActionHandler
        implements IEntityMarshalContext {

    @Inject
    protected CommonDataManager dataManager;

    public EntityHandler(EntityController controller) {
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

}
