package com.bee32.plover.orm.util.hibernate;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.bee32.plover.arch.SupportLibrary;
import com.bee32.plover.orm.dao.HibernateDaoSupport;
import com.bee32.plover.orm.dao.HibernateTemplate;
import com.bee32.plover.orm.unit.PersistenceUnit;

@Component
public class HibernateUnitDao
        extends SupportLibrary {

    @Inject
    private SessionFactoryBuilder builder;

    private PersistenceUnit[] persistenceUnits;

    private HibernateDaoSupport support;

    public HibernateUnitDao() {
    }

    public HibernateUnitDao(PersistenceUnit... persistenceUnits) {
        this(TestSessionFactoryBuilder.getInstance(), persistenceUnits);
    }

    public HibernateUnitDao(SessionFactoryBuilder builder, PersistenceUnit... persistenceUnits) {
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
