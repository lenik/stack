package com.bee32.sem.module;

import com.bee32.plover.arch.Module;
import com.bee32.plover.arch.credit.Credit;
import com.bee32.plover.arch.service.IServiceContribution;
import com.bee32.plover.arch.service.Service;
import com.bee32.plover.orm.entity.HibernateEntityRepository;
import com.bee32.plover.orm.entity.IEntity;
import com.bee32.plover.orm.entity.IEntityRepository;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.plover.orm.unit.PersistenceUnits;

/**
 * Module contributions for the menu:
 * <ul>
 * <li>(LOGO) PATH -> module, model
 * <li>FILE -> repr<br>
 * <li>VIEW -> model, renderer<br>
 * <li>BIZ -> model, piggy<br>
 * <li>NET -> internet<br>
 * <li>OPTS -> model, contrib<br>
 * <li>HELP -> model<br>
 */
public abstract class EnterpriseModule
        extends Module
        implements IEnterpriseModule {

    public EnterpriseModule() {
        super();
        preamble();

    }

    public EnterpriseModule(String name) {
        super(name);
        preamble();
    }

    @Override
    public Credit getCredit() {
        return null;
    }

    @Override
    public String getCopyright() {
        return "(C) Copyright 2011 BEE32.com, all rights reserved.";
    }

    /**
     * In the preamble, you should:
     * <ol>
     * <li>Configure the module.
     * <li>Add service contributions by {@link #contribute(IServiceContribution) contribute} method.
     * <li>Export resources, entities for public access.
     * <ol>
     */
    protected abstract void preamble();

    protected final void contribute(IServiceContribution<?> contribution) {
        Service.contribute(contribution);
    }

    protected <E extends IEntity<K>, K> void export(IEntityRepository<E, K> entityRepository, String location,
            String persistenceUnitName) {

        // declare the restful token
        declare(location, entityRepository);

        // contribute to the global persistence unit.
        Class<E> entityType = entityRepository.getEntityType();
        PersistenceUnit unit = PersistenceUnits.getInstance(persistenceUnitName);
        unit.addPersistedClass(entityType);
    }

    protected <E extends IEntity<K>, K> void export(IEntityRepository<E, K> entityRepository, String location) {
        export(entityRepository, location, PersistenceUnits.GLOBAL);
    }

    protected <E extends IEntity<K>, K> void export(IEntityRepository<E, K> entityRepository) {
        String location = entityRepository.getName();
        export(entityRepository, location);
    }

    /**
     * Export entity using default entity manager.
     *
     * Currently, this default constrction of hibernate repository is used.
     */
    protected <E extends IEntity<K>, K> void exportEntity(Class<E> entityType, Class<K> keyType) {
        exportEntityByHibernate(entityType, keyType);
    }

    /**
     * The hibernate mapping file (<code>&lt;entity-class&gt;.hbm.xml</code>).
     */
    protected <E extends IEntity<K>, K> void exportEntityByHibernate(Class<E> entityType, Class<K> keyType) {
        IEntityRepository<E, K> repository = new HibernateEntityRepository<E, K>(entityType, keyType);
        export(repository);
    }

}
