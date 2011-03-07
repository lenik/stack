package com.bee32.plover.orm.util.hibernate;

import org.hibernate.SessionFactory;

import com.bee32.plover.arch.ISupportLibrary;
import com.bee32.plover.orm.dao.HibernateDaoSupport;
import com.bee32.plover.orm.dao.HibernateTemplate;
import com.bee32.plover.orm.unit.PersistenceUnit;

public class HibernateLibrary
        implements ISupportLibrary {

    private final SessionFactoryBuilder builder;
    private final PersistenceUnit[] persistenceUnits;

    private HibernateDaoSupport support;

    public HibernateLibrary(PersistenceUnit... persistenceUnits) {
        this(TestSessionFactoryBuilder.getInstance(), persistenceUnits);
    }

    public HibernateLibrary(SessionFactoryBuilder builder, PersistenceUnit... persistenceUnits) {
        if (builder == null)
            throw new NullPointerException("builder");
        if (persistenceUnits == null)
            throw new NullPointerException("persistenceUnits");
        this.builder = builder;
        this.persistenceUnits = persistenceUnits;
    }

    protected synchronized HibernateDaoSupport getSupport() {
        if (support == null) {
            support = new HibernateDaoSupport();

            SessionFactory sessionFactory = builder.buildForUnits(persistenceUnits);
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
