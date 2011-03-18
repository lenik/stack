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
        super("test");
    }

    @Inject
    @TestPurpose
    DataSource dataSource;

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
        properties.setProperty(hbm2ddlAuto, "create");

        // Debug
        properties.setProperty(showSql, "true");
        properties.setProperty(formatSql, "true");
        properties.setProperty(useSqlComments, "true");

        // Performance
        properties.setProperty(connectionPoolSize, "1");
        properties.setProperty(cacheProviderClass, NoCacheProvider.class.getName());
    }

    protected PersistenceUnit testUnit = new PersistenceUnit("test-unit");;

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
