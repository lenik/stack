package com.bee32.plover.orm.util.hibernate;

import java.util.Properties;

import org.hibernate.cache.NoCacheProvider;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.transaction.JDBCTransactionFactory;

public class TestSessionFactoryProvider
        extends SessionFactoryProvider {

    public void configure(Properties properties) {
        // Dialect
        properties.setProperty("hibernate.dialect", H2Dialect.class.getName());

        properties.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        properties.setProperty("hibernate.connection.url", "jdbc:h2:target/testdb");
        properties.setProperty("hibernate.connection.username", "sa");
        properties.setProperty("hibernate.connection.password", "");

        // Transaction
        properties.setProperty("hibernate.transaction.factory_class", JDBCTransactionFactory.class.getName());
        properties.setProperty("hibernate.connection.autocommit", "false");

        // Mapping
        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        properties.setProperty("hibernate.id.new_generator_mappings", "true");

        // Performance
        properties.setProperty("hibernate.connection.pool_size", "1");
        properties.setProperty("hibernate.cache.provider_class", NoCacheProvider.class.getName());

        properties.setProperty("org.hibernate.flushMode", "COMMIT");
        properties.setProperty("hibernate.generate_statistics", "true");

        // Debug
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "true");
        properties.setProperty("hibernate.use_sql_comments", "true");

        // Misc
        properties.setProperty("hibernate.current_session_context_class", "thread");
    }

    static final TestSessionFactoryProvider instance = new TestSessionFactoryProvider();

    public static TestSessionFactoryProvider getInstance() {
        return instance;
    }

}
