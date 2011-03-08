package com.bee32.plover.orm.util.hibernate;

import org.hibernate.cache.NoCacheProvider;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.transaction.JDBCTransactionFactory;
import org.springframework.stereotype.Component;

@Component("test")
public class TestSessionFactoryBuilder
        extends SessionFactoryBuilder {

    public TestSessionFactoryBuilder() {
        // Dialect
        setProperty("hibernate.dialect", H2Dialect.class.getName());

        setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        setProperty("hibernate.connection.url", "jdbc:h2:target/testdb");
        setProperty("hibernate.connection.username", "sa");
        setProperty("hibernate.connection.password", "");

        // Transaction
        setProperty("hibernate.transaction.factory_class", JDBCTransactionFactory.class.getName());
        setProperty("hibernate.connection.autocommit", "false");

        // Mapping
        setProperty("hibernate.hbm2ddl.auto", "create-drop");
        setProperty("hibernate.id.new_generator_mappings", "true");

        // Performance
        setProperty("hibernate.connection.pool_size", "1");
        setProperty("hibernate.cache.provider_class", NoCacheProvider.class.getName());

        setProperty("org.hibernate.flushMode", "COMMIT");
        setProperty("hibernate.generate_statistics", "true");

        // Debug
        setProperty("hibernate.show_sql", "true");
        setProperty("hibernate.format_sql", "true");
        setProperty("hibernate.use_sql_comments", "true");

        // Misc
        setProperty("hibernate.current_session_context_class", "thread");
    }

    static final TestSessionFactoryBuilder instance = new TestSessionFactoryBuilder();

    public static TestSessionFactoryBuilder getInstance() {
        return instance;
    }

}
