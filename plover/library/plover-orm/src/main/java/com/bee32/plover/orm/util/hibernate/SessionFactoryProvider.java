package com.bee32.plover.orm.util.hibernate;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

import com.bee32.plover.orm.unit.PersistenceUnit;

public abstract class SessionFactoryProvider {

    protected abstract void configure(Properties properties);

    public SessionFactory getSessionFactory(PersistenceUnit persistenceUnit) {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();

        Properties properties = new Properties();
        configure(properties);
        sessionFactoryBean.setHibernateProperties(properties);

        String[] resources = persistenceUnit.getMappingResources();
        sessionFactoryBean.setMappingResources(resources);
        try {
            sessionFactoryBean.afterPropertiesSet();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        return sessionFactoryBean.getObject();
    }

}
