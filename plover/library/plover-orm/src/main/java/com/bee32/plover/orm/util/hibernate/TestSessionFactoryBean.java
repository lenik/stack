package com.bee32.plover.orm.util.hibernate;

import java.util.Properties;

import org.hibernate.cache.NoCacheProvider;
import org.hibernate.dialect.H2Dialect;

import com.bee32.plover.inject.qualifier.RunConfig;

@RunConfig("test")
public class TestSessionFactoryBean
        extends CandidateSessionFactoryBean {

    public TestSessionFactoryBean() {
        super("test");
    }

    @Override
    protected void populateHibernateProperties(Properties properties) {
        super.populateHibernateProperties(properties);

        // Dialect
        properties.setProperty("hibernate.dialect", H2Dialect.class.getName());

        properties.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        properties.setProperty("hibernate.connection.url", "jdbc:h2:target/testdb");
        properties.setProperty("hibernate.connection.username", "sa");
        properties.setProperty("hibernate.connection.password", "");

        // Mapping
        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");

        // Performance
        properties.setProperty("hibernate.connection.pool_size", "1");
        properties.setProperty("hibernate.cache.provider_class", NoCacheProvider.class.getName());
    }

}
