package com.bee32.plover.orm.util.hibernate;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Assert;

import com.bee32.plover.orm.dao.HibernateDaoSupport;
import com.bee32.plover.orm.dao.HibernateTemplate;
import com.bee32.plover.orm.unit.PersistenceUnit;

public class HibernateSessionTestCase
        extends Assert {

    private final PersistenceUnit[] persistenceUnits;
    private HibernateDaoSupport support;

    public HibernateSessionTestCase(PersistenceUnit... persistenceUnits) {
        if (persistenceUnits == null)
            throw new NullPointerException("persistenceUnits");
        this.persistenceUnits = persistenceUnits;
    }

    protected SessionFactoryBuilder getSessionFactoryBuilder() {
        return TestSessionFactoryBuilder.getInstance();
    }

    protected synchronized HibernateDaoSupport getSupport() {
        if (support == null) {
            support = new HibernateDaoSupport();

            SessionFactory sessionFactory = getSessionFactoryBuilder().buildForUnits(persistenceUnits);
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

    @After
    public void tearDown() {
        if (support != null) {
            SessionFactory sessionFactory = support.getSessionFactory();
            if (sessionFactory != null)
                sessionFactory.close();
        }
    }

}
