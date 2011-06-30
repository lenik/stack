package com.bee32.plover.orm.util;

import java.io.Serializable;

import javax.free.IllegalUsageException;

import com.bee32.plover.arch.Module;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityRepository;
import com.bee32.plover.orm.entity.GenericEntityDao;
import com.bee32.plover.orm.entity.IEntityRepo;

public abstract class ERModule
        extends Module {

    public ERModule() {
        super();
    }

    public ERModule(String name) {
        super(name);
    }

    protected <ER extends EntityRepository<?, ?>> //
    void export(Class<ER> erClass) {
        // declare the restful token
        ER repo = applicationContext.getBean(erClass);

        // Type[] pv = ClassUtil.getOriginPV(erClass);
        Class<?> entityType = repo.getEntityType();
        Alias aliasAnn = entityType.getAnnotation(Alias.class);
        if (aliasAnn == null)
            throw new IllegalUsageException("No @Alias defined on " + entityType);

        export(erClass, aliasAnn.value());
    }

    protected <ER extends EntityRepository<?, ?>> //
    void export(Class<ER> erClass, String location) {
        // declare the restful token
        ER repo = applicationContext.getBean(erClass);
        declare(location, repo);
    }

    protected <E extends Entity<K>, K extends Serializable> //
    void export(IEntityRepo<E, K> entityRepository, String location) {

        // declare the restful token
        declare(location, entityRepository);
    }

    protected <E extends Entity<K>, K extends Serializable> //
    void export(IEntityRepo<E, K> entityRepository) {
        String location = entityRepository.getName();
        export(entityRepository, location);
    }

    /**
     * Export entity using default entity manager.
     *
     * Currently, this default constrction of hibernate repository is used.
     */
    protected <E extends Entity<K>, K extends Serializable> //
    void exportEntity(Class<E> entityType, Class<K> keyType) {
        exportEntityByHibernate(entityType, keyType);
    }

    /**
     * The hibernate mapping file (<code>&lt;entity-class&gt;.hbm.xml</code>).
     */
    protected <E extends Entity<K>, K extends Serializable> //
    void exportEntityByHibernate(Class<E> entityType, Class<K> keyType) {
        IEntityRepo<E, K> repository = new GenericEntityDao<E, K>(entityType, keyType);
        export(repository);
    }

}
