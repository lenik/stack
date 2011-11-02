package com.bee32.plover.orm.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.hibernate.transaction.JDBCTransactionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bee32.plover.arch.util.OrderComparator;
import com.bee32.plover.orm.PloverNamingStrategy;
import com.bee32.plover.orm.unit.PUnitDumper;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.plover.site.scope.PerSite;
import com.bee32.plover.thirdparty.hibernate.util.HibernateProperties;
import com.bee32.plover.thirdparty.hibernate.util.MappingResourceUtil;

@Component
@PerSite
public class CustomizedSessionFactoryBean
        extends LazySessionFactoryBean
        implements HibernateProperties {

    static final Logger logger = LoggerFactory.getLogger(CustomizedSessionFactoryBean.class);

    static final Set<IDatabaseConfigurer> configurers;
    static {
        configurers = new TreeSet<IDatabaseConfigurer>(OrderComparator.INSTANCE);
        for (IDatabaseConfigurer sfc : ServiceLoader.load(IDatabaseConfigurer.class)) {
            configurers.add(sfc);
        }
    }

    public CustomizedSessionFactoryBean(String name) {
        super(name);
    }

    static boolean usingAnnotation = true;

    static PersistenceUnit staticUnit = PersistenceUnit.getDefault();

    public static PersistenceUnit getForceUnit() {
        return staticUnit;
    }

    public static void setForceUnit(PersistenceUnit forceUnit) {
        if (forceUnit == null)
            throw new NullPointerException("forceUnit");
        CustomizedSessionFactoryBean.staticUnit = forceUnit;
        forceUnit.select();
    }

    @Override
    protected void lazyConfigure() {

        // Prepare hibernate overrides
        Properties properties = new Properties();
        populateHibernateProperties(properties);

        for (IDatabaseConfigurer sfc : configurers) {
            try {
                sfc.configureHibernateProperties(properties);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }

        if (logger.isDebugEnabled()) {
            logger.debug("CSFB Properties: ");

            List<String> names = new ArrayList<String>();
            for (Object key : properties.keySet())
                names.add((String) key);
            Collections.sort(names);

            for (String name : names) {
                Object value = properties.get(name);
                logger.debug("    " + name + " = " + value);
            }
        }

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
        properties.setProperty(transactionFactoryClassKey, //
                JDBCTransactionFactory.class.getName());

        properties.setProperty(connectionAutocommitKey, "false");

        // Mapping
        properties.setProperty("hibernate.globally_quoted_identifiers", "true");

        properties.setProperty("hibernate.id.new_generator_mappings", "true");

        properties.setProperty("org.hibernate.flushMode", "COMMIT");

        // Misc
        properties.setProperty(currentSessionContextClassKey, "thread");

    }

    @Inject
    @Override
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

}
