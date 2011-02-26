package com.bee32.plover.orm.util.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

public class ConfiguredSessionFactoryBean
        extends LocalSessionFactoryBean {

    private final Configuration configuration;

    public ConfiguredSessionFactoryBean(Configuration configuration) {
        if (configuration == null)
            throw new NullPointerException("configuration");
        this.configuration = configuration;
    }

    @Override
    protected Configuration newConfiguration()
            throws HibernateException {
        return configuration;
    }

}
