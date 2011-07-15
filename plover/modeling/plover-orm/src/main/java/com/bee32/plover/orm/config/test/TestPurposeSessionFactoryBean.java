package com.bee32.plover.orm.config.test;

import java.util.Properties;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.hibernate.cache.NoCacheProvider;
import org.hibernate.dialect.H2Dialect;

import com.bee32.plover.orm.config.CustomizedSessionFactoryBean;

public abstract class TestPurposeSessionFactoryBean
        extends CustomizedSessionFactoryBean {

    @Inject
    DataSource dataSource;

    public TestPurposeSessionFactoryBean() {
        super("test");
    }

    @Override
    protected void populateHibernateProperties(Properties properties) {
        super.populateHibernateProperties(properties);

        // Dialect
        properties.setProperty(dialect, H2Dialect.class.getName());

        /**
         * If set, this will override corresponding settings in Hibernate properties.
         *
         * If this is set, the Hibernate settings should not define a connection provider to avoid
         * meaningless double configuration.
         */
        setDataSource(dataSource);

        // Mapping
        properties.setProperty(hbm2ddlAuto, "create-drop");

        // Debug
        properties.setProperty(showSql, "false"); // true
        properties.setProperty(formatSql, "true");
        properties.setProperty(useSqlComments, "true");

        // Performance
        properties.setProperty(connectionPoolSize, "1");
        properties.setProperty(cacheProviderClass, NoCacheProvider.class.getName());
    }

}
