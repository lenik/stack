package com.bee32.plover.orm.util;

import java.io.Serializable;

import javax.free.IllegalUsageException;

import com.bee32.plover.arch.Module;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityRepository;
import com.bee32.plover.orm.entity.GenericEntityDao;
import com.bee32.plover.orm.entity.IEntityRepo;
import com.bee32.plover.restful.resource.IObjectPageDirectory;
import com.bee32.plover.restful.resource.PageDirectory;
import com.bee32.plover.rtx.location.Location;

public abstract class ERModule
        extends Module {

    public ERModule() {
        super();
    }

    public ERModule(String name) {
        super(name);
    }

    protected void declareEntityPages(Class<? extends Entity<?>> entityType, String shortName) {
        ModuleEntityPageDirectory pageDir = new ModuleEntityPageDirectory(this, entityType, shortName + "/");
        PageDirectory.register(entityType, pageDir);
    }

    protected <ER extends EntityRepository<?, ?>> //
    void export(Class<ER> erClass) {
        // declare the restful token
        ER repo = applicationContext.getBean(erClass);

        // Type[] pv = ClassUtil.getOriginPV(erClass);
        Class<?> entityType = repo.getEntityType();
        IObjectPageDirectory pageDir = PageDirectory.getPageDirectory(entityType);
        Location baseLocation = pageDir.getBaseLocation();
        if (baseLocation == null)
            throw new IllegalUsageException("Base location isn't defined for " + entityType);

        String baseName = baseLocation.getBase();
        int lastSlash = baseName.lastIndexOf('/');
        if (lastSlash != -1)
            baseName = baseName.substring(lastSlash + 1);

        // Well, this base name maybe not very accuracy.
        // but, since the restful isn't in use yet, just let it go...
        export(erClass, baseName);
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
