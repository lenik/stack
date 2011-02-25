package com.bee32.sem.module;

import com.bee32.plover.arch.Module;
import com.bee32.plover.arch.credit.Credit;
import com.bee32.plover.arch.service.IServiceContribution;
import com.bee32.plover.arch.service.Service;
import com.bee32.plover.orm.entity.EntityRepository;
import com.bee32.plover.orm.entity.IEntity;
import com.bee32.plover.orm.unit.IPersistenceUnit;

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

    protected abstract void preamble();

    protected final void contribute(IServiceContribution<?> contribution) {
        Service.contribute(contribution);
    }

    protected <E extends IEntity<K>, K> void service(EntityRepository<E, K> entityRepository, String location,
            String persistenceUnitName) {

        // declare the restful token
        declare(location, entityRepository);

        // contribute to the persistence unit.
        entityRepository.getEntityType();
    }

    protected <E extends IEntity<K>, K> void service(EntityRepository<E, K> entityRepository, String location) {
        service(entityRepository, location, IPersistenceUnit.GLOBAL);
    }

    protected <E extends IEntity<K>, K> void service(EntityRepository<E, K> entityRepository) {
        String location = entityRepository.getName();
        service(entityRepository, location);
    }

}
