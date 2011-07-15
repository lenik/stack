package com.bee32.plover.orm.config.main;

import java.util.Properties;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.hibernate.cache.NoCacheProvider;
import org.hibernate.dialect.PostgreSQLDialect;

import com.bee32.plover.orm.config.CustomizedSessionFactoryBean;

public abstract class MainPurposeSessionFactoryBean
        extends CustomizedSessionFactoryBean {

    @Inject
    DataSource dataSource;

    public MainPurposeSessionFactoryBean() {
        super("main");
    }

    @Override
    protected void populateHibernateProperties(Properties properties) {
        super.populateHibernateProperties(properties);

        // Dialect
        properties.setProperty(dialect, PostgreSQLDialect.class.getName());

        /**
         * If set, this will override corresponding settings in Hibernate properties.
         *
         * If this is set, the Hibernate settings should not define a connection provider to avoid
         * meaningless double configuration.
         */
        setDataSource(dataSource);

        // Mapping
        properties.setProperty(hbm2ddlAuto, "validate"); // opt. away in userconf

        // Debug
        properties.setProperty(showSql, "false"); // opt. away in userconf
        properties.setProperty(formatSql, "false");
        properties.setProperty(useSqlComments, "false");

        // Performance
        properties.setProperty(connectionPoolSize, "100");

        // TODO: The CacheProvider is deprecated.
        properties.setProperty(cacheProviderClass, NoCacheProvider.class.getName());
    }

}
