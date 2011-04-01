package com.bee32.plover.orm.config;

import java.util.Collection;
import java.util.Map.Entry;
import java.util.Properties;

import org.hibernate.transaction.JDBCTransactionFactory;

import com.bee32.plover.orm.PloverNamingStrategy;
import com.bee32.plover.orm.unit.PUnitDumper;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.plover.thirdparty.hibernate.util.HibernateProperties;
import com.bee32.plover.thirdparty.hibernate.util.MappingResourceUtil;

public abstract class CustomizedSessionFactoryBean
        extends LazySessionFactoryBean
        implements HibernateProperties {

    private Properties overrides;

    public CustomizedSessionFactoryBean(String name) {
        super(name);

        this.overrides = new Properties();

        Properties properties = DataConfig.getProperties(name);

        for (Entry<Object, Object> entry : properties.entrySet()) {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            // if (key.startsWith("hibernate.connection."))
            // continue;
            overrides.put(key, value);
        }
    }

    static boolean usingAnnotation = true;

    static PersistenceUnit staticUnit = PersistenceUnit.getDefault();

    public static void setForceUnit(PersistenceUnit forceUnit) {
        if (forceUnit == null)
            throw new NullPointerException("forceUnit");
        CustomizedSessionFactoryBean.staticUnit = forceUnit;
    }

    @Override
    protected void lazyConfigure() {

        // Prepare hibernate overrides
        Properties properties = new Properties();
        populateHibernateProperties(properties);

        // Add overrides
        properties.putAll(overrides);

        this.setHibernateProperties(properties);

        // Naming strategy (used to escape the name)
        String hibernateDialect = (String) properties.get("hibernate.dialect");
        this.setNamingStrategy(PloverNamingStrategy.getInstance(hibernateDialect));

        // Merge mapping resources

        if (logger.isDebugEnabled()) {
            logger.info("Static Unit Dump:\n" + PUnitDumper.format(staticUnit));
        }

        if (usingAnnotation) {

            // this.setConfigurationClass(AnnotationConfiguration.class);
            // see newConfiguration overrides.

            Collection<Class<?>> persistentClasses = staticUnit.getClasses();

            Class<?>[] persistentClassArray = persistentClasses.toArray(new Class<?>[0]);
            this.setAnnotatedClasses(persistentClassArray);

        } else {
            String[] allResources = MappingResourceUtil.getMappingResources(staticUnit);

            if (logger.isInfoEnabled()) {
                logger.info("Resource mappings for SFB " + getName());
                for (String resource : allResources)
                    logger.info("Resource for mapping: " + resource);
            }

            this.setMappingResources(allResources);
        }
    }

    /**
     * Populate the hibernate properties.
     */
    protected void populateHibernateProperties(Properties properties) {

        // Transaction
        properties.setProperty(transactionFactoryClass, //
                JDBCTransactionFactory.class.getName());

        properties.setProperty(connectionAutocommit, "false");

        // Mapping
        properties.setProperty("hibernate.globally_quoted_identifiers", "true");

        properties.setProperty("hibernate.id.new_generator_mappings", "true");

        properties.setProperty("org.hibernate.flushMode", "COMMIT");
        properties.setProperty(generateStatistics, "true");

        // Debug
        properties.setProperty(showSql, "true");

        // Misc
        properties.setProperty(currentSessionContextClass, "thread");

    }

}
