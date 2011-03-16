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
        properties.setProperty(dialect, H2Dialect.class.getName());

        setDataSource(dataSource);

        // Mapping
        properties.setProperty(hbm2ddlAuto, "create-drop");

        // Debug
        properties.setProperty(showSql, "true");
        properties.setProperty(formatSql, "true");
        properties.setProperty(useSqlComments, "true");

        // Performance
        properties.setProperty(connectionPoolSize, "1");
        properties.setProperty(cacheProviderClass, NoCacheProvider.class.getName());
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
