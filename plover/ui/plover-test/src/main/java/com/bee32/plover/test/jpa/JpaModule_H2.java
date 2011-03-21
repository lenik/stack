package com.bee32.plover.test.jpa;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaModule_H2
        extends JpaModule {

    @Override
    protected EntityManagerFactory createEntityManagerFactory() {
        Map<String, String> properties = new HashMap<String, String>();
        properties.put("hibernate.connection.driver_class", "org.h2.Driver");
        properties.put("hibernate.connection.url", "jdbc:h2:target/testdb");
        properties.put("hibernate.connection.username", "sa");
        properties.put("hibernate.connection.password", "");
        properties.put("hibernate.connection.pool_size", "1");
        properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        properties.put("hibernate.hbm2ddl.auto", "create");
        return Persistence.createEntityManagerFactory("plover-test", properties);
    }

}
