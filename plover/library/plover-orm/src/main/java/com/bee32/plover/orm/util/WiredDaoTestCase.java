package com.bee32.plover.orm.util;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.springframework.test.context.ContextConfiguration;

import com.bee32.plover.orm.dao.HibernateDaoSupport;
import com.bee32.plover.orm.dao.HibernateTemplate;
import com.bee32.plover.orm.test.DefaultTestSessionFactoryBean;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.plover.orm.unit.PersistenceUnitSelection;
import com.bee32.plover.test.WiredAssembledTestCase;

@ContextConfiguration("/com/bee32/plover/orm/context/tx-context.xml")
public abstract class WiredDaoTestCase
        extends WiredAssembledTestCase {

    @Inject
    private SessionFactory sessionFactory;

    private transient HibernateDaoSupport support;

    public WiredDaoTestCase(PersistenceUnit... persistenceUnits) {
        PersistenceUnitSelection puSelection = PersistenceUnitSelection.getContextSelection(//
                DefaultTestSessionFactoryBean.class);

        for (PersistenceUnit unit : persistenceUnits)
            puSelection.remove(unit);

        puSelection.add(persistenceUnits);
    }

    protected HibernateDaoSupport getSupport() {
        if (support == null) {
            synchronized (this) {
                if (support == null) {
                    support = new HibernateDaoSupport();
                    support.setSessionFactory(sessionFactory);
                    support.afterPropertiesSet();
                }
            }
        }
        return support;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public HibernateTemplate getHibernateTemplate() {
        return getSupport().getHibernateTemplateEx();
    }

}
