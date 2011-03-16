package com.bee32.plover.orm.config;

import java.util.Properties;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.hibernate.cache.NoCacheProvider;
import org.hibernate.dialect.H2Dialect;

import com.bee32.plover.inject.qualifier.TestPurpose;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.plover.orm.unit.PersistenceUnitSelection;

public abstract class TestPurposeSessionFactoryBean
        extends CustomizedSessionFactoryBean {

    public TestPurposeSessionFactoryBean() {
        super("test-purpose");
    }

    public TestPurposeSessionFactoryBean(String name) {
        super(name);
    }

    @Inject
    @TestPurpose
    DataSource dataSource;

    @Override
    protected void populateHibernateProperties(Properties properties) {
        super.populateHibernateProperties(properties);

        // Dialect
        properties.setProperty("hibernate.dialect", H2Dialect.class.getName());

        setDataSource(dataSource);

        // Mapping
        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");

        // Performance
        properties.setProperty("hibernate.connection.pool_size", "1");
        properties.setProperty("hibernate.cache.provider_class", NoCacheProvider.class.getName());
    }

    private PersistenceUnit testUnit = new PersistenceUnit("test-unit");;

    public void addPersistedClass(Class<?> clazz) {
        testUnit.addPersistedClass(clazz);
    }

    public void removePersistedClass(Class<?> clazz) {
        testUnit.removePersistedClass(clazz);
    }

    @Override
    protected PersistenceUnitSelection getPersistenceUnitSelection() {
        PersistenceUnitSelection selection = new PersistenceUnitSelection();
        selection.add(testUnit);
        return selection;
    }

}
