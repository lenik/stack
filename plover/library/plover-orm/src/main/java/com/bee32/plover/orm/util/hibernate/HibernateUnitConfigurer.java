package com.bee32.plover.orm.util.hibernate;

import org.hibernate.SessionFactory;

import com.bee32.plover.arch.SupportLibrary;
import com.bee32.plover.orm.dao.HibernateDaoSupport;
import com.bee32.plover.orm.dao.HibernateTemplate;
import com.bee32.plover.orm.unit.PersistenceUnit;

public class HibernateUnitConfigurer
        extends SupportLibrary {

    private HibernateConfigurer hibernateConfigurer;

    private PersistenceUnit[] persistenceUnits;

    private HibernateDaoSupport support;

    public HibernateUnitConfigurer() {
    }

    public HibernateUnitConfigurer(HibernateConfigurer hibernateConfigurer, PersistenceUnit... persistenceUnits) {
        if (hibernateConfigurer == null)
            throw new NullPointerException("hibernateConfigurer");

        if (persistenceUnits == null)
            throw new NullPointerException("persistenceUnits");

        this.hibernateConfigurer = hibernateConfigurer;
        this.persistenceUnits = persistenceUnits;
    }

    protected synchronized HibernateDaoSupport getSupport() {
        if (support == null) {
            support = new HibernateDaoSupport();

            SessionFactory sessionFactory = hibernateConfigurer.getSessionFactory(persistenceUnits);
            // factoryBuilder.setProperty("hibernate.hbm2ddl.auto", "create-drop");

            support.setSessionFactory(sessionFactory);
        }
        return support;
    }

    public SessionFactory getSessionFactory() {
        return getSupport().getSessionFactory();
    }

    public HibernateTemplate getHibernateTemplate() {
        return getSupport().getHibernateTemplateEx();
    }

    @Override
    public void startup()
            throws Exception {
    }

    @Override
    public void shutdown()
            throws Exception {
        if (support != null) {
            SessionFactory sessionFactory = support.getSessionFactory();
            if (sessionFactory != null)
                sessionFactory.close();
        }
    }

}
