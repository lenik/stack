package com.bee32.plover.orm.util;

import java.io.Serializable;

import com.bee32.plover.arch.Module;
import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.plover.orm.entity.EntityRepository;
import com.bee32.plover.orm.entity.HibernateEntityRepository;
import com.bee32.plover.orm.entity.IEntityRepository;

public abstract class ERModule
        extends Module {

    public ERModule() {
        super();
    }

    public ERModule(String name) {
        super(name);
    }

    protected <ER extends EntityRepository<?, ?>> //
    void export(Class<ER> erClass, String location) {
        // declare the restful token
        // declare(location, entityRepository);
    }

    protected <E extends EntityBean<K>, K extends Serializable> //
    void export(IEntityRepository<E, K> entityRepository, String location) {

        // declare the restful token
        declare(location, entityRepository);
    }

    protected <E extends EntityBean<K>, K extends Serializable> //
    void export(IEntityRepository<E, K> entityRepository) {
        String location = entityRepository.getName();
        export(entityRepository, location);
    }

    /**
     * Export entity using default entity manager.
     *
     * Currently, this default constrction of hibernate repository is used.
     */
    protected <E extends EntityBean<K>, K extends Serializable> //
    void exportEntity(Class<E> entityType, Class<K> keyType) {
        exportEntityByHibernate(entityType, keyType);
    }

    /**
     * The hibernate mapping file (<code>&lt;entity-class&gt;.hbm.xml</code>).
     */
    protected <E extends EntityBean<K>, K extends Serializable> //
    void exportEntityByHibernate(Class<E> entityType, Class<K> keyType) {
        IEntityRepository<E, K> repository = new HibernateEntityRepository<E, K>(entityType, keyType);
        export(repository);
    }

}
