package com.bee32.plover.orm.config;

import java.lang.reflect.Array;
import java.util.*;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;
import org.hibernate.event.EventListeners;
import org.hibernate.transaction.JDBCTransactionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.bee32.plover.arch.util.OrderComparator;
import com.bee32.plover.inject.spring.ScopeProxy;
import com.bee32.plover.orm.PloverNamingStrategy;
import com.bee32.plover.orm.access.EntityProcessors;
import com.bee32.plover.orm.access.HibernateEvents;
import com.bee32.plover.orm.unit.PUnitDumper;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.plover.site.SiteException;
import com.bee32.plover.site.SiteInstance;
import com.bee32.plover.site.scope.PerSite;
import com.bee32.plover.thirdparty.hibernate.util.IHibernatePropertyNames;
import com.bee32.plover.thirdparty.hibernate.util.MappingResourceUtil;

@Component
@PerSite
@ScopeProxy(ScopedProxyMode.INTERFACES)
public class SiteSessionFactoryBean
        extends LazySessionFactoryBean
        implements IHibernatePropertyNames {

    static final Logger logger = LoggerFactory.getLogger(SiteSessionFactoryBean.class);

    static final Set<IDatabaseConfigurer> configurers;
    static {
        configurers = new TreeSet<IDatabaseConfigurer>(OrderComparator.INSTANCE);
        for (IDatabaseConfigurer dc : ServiceLoader.load(IDatabaseConfigurer.class)) {
            configurers.add(dc);
        }
    }

    static boolean usingAnnotation = true;

    static PersistenceUnit staticUnit = PersistenceUnit.getDefault();

    public static PersistenceUnit getForceUnit() {
        return staticUnit;
    }

    public static void setForceUnit(PersistenceUnit forceUnit) {
        if (forceUnit == null)
            throw new NullPointerException("forceUnit");
        SiteSessionFactoryBean.staticUnit = forceUnit;
        forceUnit.select();
    }

    SiteInstance site;

    public SiteSessionFactoryBean()
            throws SiteException {
        site = ThreadHttpContext.getSiteInstance();
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
        String hibernateDialect = (String) properties.get(dialectKey);
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
                logger.info("Resource mappings for site " + site.getName());
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

    @Override
    protected void postProcessConfiguration(Configuration config)
            throws HibernateException {
        EventListeners eventListeners = config.getEventListeners();
        for (Entry<String, Object> entry : EntityProcessors.getInstance().getEventListeners().entrySet()) {
            String eventName = entry.getKey();
            List<?> listeners = (List<?>) entry.getValue();

            Class<?> eventType = HibernateEvents.getEventType(eventName);
            Object[] oldListeners = HibernateEvents.getEventListeners(eventListeners, eventName);

            Object[] join = (Object[]) Array.newInstance(eventType, listeners.size() + oldListeners.length);
            System.arraycopy(oldListeners, 0, join, listeners.size(), oldListeners.length);
            int index = 0; // oldListeners.length;
            for (Object listener : listeners)
                join[index++] = listener;

            HibernateEvents.setEventListeners(eventListeners, eventName, join);
        }
    }

    @Inject
    @Override
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

}
