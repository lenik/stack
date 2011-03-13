package com.bee32.plover.orm.util.hibernate;

import java.util.Properties;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.hibernate.cache.NoCacheProvider;
import org.hibernate.dialect.H2Dialect;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.bee32.plover.inject.qualifier.TestPurpose;

@Component
@TestPurpose
@Lazy
public class TestSessionFactoryBean
        extends CandidateSessionFactoryBean {

    @Inject
    @TestPurpose
    DataSource dataSource;

    public TestSessionFactoryBean() {
        super("test-sfb");
    }

    @Override
    protected void populateHibernateProperties(Properties properties) {
        super.populateHibernateProperties(properties);

        // Dialect
        properties.setProperty("hibernate.dialect", H2Dialect.class.getName());

        setDataSource(dataSource);

        // Mapping
        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");

        // Performance
        properties.setProperty("hibernate.connection.pool_size", "3");
        properties.setProperty("hibernate.cache.provider_class", NoCacheProvider.class.getName());
    }

}
