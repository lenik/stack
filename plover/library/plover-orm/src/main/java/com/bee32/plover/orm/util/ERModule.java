package com.bee32.plover.orm.util;

import java.io.Serializable;

import com.bee32.plover.arch.Module;
import com.bee32.plover.orm.entity.HibernateEntityRepository;
import com.bee32.plover.orm.entity.IEntity;
import com.bee32.plover.orm.entity.IEntityRepository;
import com.bee32.plover.orm.unit.PUnit;

public abstract class ERModule
        extends Module {

    public ERModule() {
        super();
    }

    public ERModule(String name) {
        super(name);
    }

    protected <E extends IEntity<K>, K extends Serializable> //
    void export(IEntityRepository<E, K> entityRepository, String location) {

        // declare the restful token
        declare(location, entityRepository);

        // contribute to the global persistence unit.
        Class<? extends E> entityType = entityRepository.getEntityType();

        PUnit.getDefault().add(entityType);
    }

    protected <E extends IEntity<K>, K extends Serializable> //
    void export(IEntityRepository<E, K> entityRepository) {
        String location = entityRepository.getName();
        export(entityRepository, location);
    }

    /**
     * Export entity using default entity manager.
     *
     * Currently, this default constrction of hibernate repository is used.
     */
    protected <E extends IEntity<K>, K extends Serializable> //
    void exportEntity(Class<E> entityType, Class<K> keyType) {
        exportEntityByHibernate(entityType, keyType);
    }

    /**
     * The hibernate mapping file (<code>&lt;entity-class&gt;.hbm.xml</code>).
     */
    protected <E extends IEntity<K>, K extends Serializable> //
    void exportEntityByHibernate(Class<E> entityType, Class<K> keyType) {
        IEntityRepository<E, K> repository = new HibernateEntityRepository<E, K>(entityType, keyType);
        export(repository);
    }

}
