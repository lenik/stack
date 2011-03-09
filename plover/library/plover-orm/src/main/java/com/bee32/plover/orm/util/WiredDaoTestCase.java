package com.bee32.plover.orm.util;

import javax.inject.Inject;

import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.plover.orm.util.hibernate.HibernateConfigurer;
import com.bee32.plover.orm.util.hibernate.HibernateUnitConfigurer;
import com.bee32.plover.test.WiredAssembledTestCase;

public class WiredDaoTestCase
        extends WiredAssembledTestCase {

    @Inject
    private HibernateConfigurer hibernateConfigurer;

    private HibernateUnitConfigurer unitConfigurer;

    private final PersistenceUnit[] persistenceUnits;

    public WiredDaoTestCase(PersistenceUnit... persistenceUnits) {
        this.persistenceUnits = persistenceUnits;
    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        install(unitConfigurer = new HibernateUnitConfigurer(hibernateConfigurer, persistenceUnits));
    }

}
