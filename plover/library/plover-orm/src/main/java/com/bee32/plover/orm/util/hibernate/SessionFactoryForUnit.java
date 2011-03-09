package com.bee32.plover.orm.util.hibernate;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.FactoryBean;

import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.plover.orm.unit.PersistenceUnits;
import com.bee32.plover.thirdparty.hibernate.util.ProxySessionFactory;

public class SessionFactoryForUnit
        extends ProxySessionFactory
        implements BeanNameAware, FactoryBean<SessionFactory> {

    private static final long serialVersionUID = 1L;

    private String beanName;

    @Inject
    private HibernateConfigurer builder;

    private SessionFactory sessionFactory;

    public SessionFactoryForUnit() {
        super(null);
    }

    @Override
    public void setBeanName(String name) {
        beanName = name;
    }

    @Override
    public Class<?> getObjectType() {
        return SessionFactory.class;
    }

    @Override
    public SessionFactory getObject()
            throws Exception {
        if (sessionFactory == null) {

            synchronized (this) {
                if (sessionFactory == null) {

                    PersistenceUnit persistenceUnit;

                    if (beanName == null || "default".equals(beanName))
                        persistenceUnit = PersistenceUnits.getInstance();
                    else
                        persistenceUnit = PersistenceUnits.getInstance(beanName);

                    sessionFactory = builder.getSessionFactory(persistenceUnit);
                }
            }

        }
        return sessionFactory;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
