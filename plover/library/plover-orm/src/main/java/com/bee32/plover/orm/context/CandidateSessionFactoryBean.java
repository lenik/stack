package com.bee32.plover.orm.context;

import java.util.Collection;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;

import com.bee32.plover.orm.unit.PersistenceUnitSelection;
import com.bee32.plover.thirdparty.hibernate.util.LazyInitSessionFactory;

public abstract class CandidateSessionFactoryBean
        extends AnnotationSessionFactoryBean {

    private final String name;

    public CandidateSessionFactoryBean(String name) {
        if (name == null)
            throw new NullPointerException("name");

        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Populate the hibernate properties.
     */
    protected void populateHibernateProperties(Properties properties) {

        // Transaction
        // properties.setProperty("hibernate.transaction.factory_class", JDBCTransactionFactory.class.getName());
        properties.setProperty("hibernate.connection.autocommit", "false");

        // Mapping
        properties.setProperty("hibernate.id.new_generator_mappings", "true");

        properties.setProperty("org.hibernate.flushMode", "COMMIT");
        properties.setProperty("hibernate.generate_statistics", "true");

        // Debug
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "true");
        properties.setProperty("hibernate.use_sql_comments", "true");

        // Misc
        properties.setProperty("hibernate.current_session_context_class", "thread");

    }

    /**
     * The persistence units for the session.
     */
    protected PersistenceUnitSelection getPersistenceUnitSelection() {
        Class<?> sfbClass = getClass();

        return PersistenceUnitSelection.getContextSelection(sfbClass);
    }

    @Override
    public void afterPropertiesSet()
            throws Exception {
        super.afterPropertiesSet();
    }

    static boolean usingAnnotation = true;

    protected void lazyConfigure() {

        // SelectedSessionFactory

        Properties hibernateProperties = new Properties();
        populateHibernateProperties(hibernateProperties);
        this.setHibernateProperties(hibernateProperties);

        // Merge mapping resources
        PersistenceUnitSelection selection = getPersistenceUnitSelection();

        if (usingAnnotation) {

            // this.setConfigurationClass(AnnotationConfiguration.class);
            // see newConfiguration overrides.

            Collection<Class<?>> persistentClasses = selection.mergePersistentClasses();

            Class<?>[] persistentClassArray = persistentClasses.toArray(new Class<?>[0]);
            this.setAnnotatedClasses(persistentClassArray);

        } else {
            Collection<String> allResources = selection.mergeMappingResources();

            if (logger.isInfoEnabled()) {
                logger.info("Resource mappings for SFB " + getName());
                for (String resource : allResources)
                    logger.info("Resource for mapping: " + resource);
            }

            if (!allResources.isEmpty()) {
                String[] resourceArray = allResources.toArray(new String[0]);
                this.setMappingResources(resourceArray);
            }
        }
    }

// @Override
    protected Configuration newConfiguration11()
            throws HibernateException {
        Configuration conf = super.newConfiguration();
        if (!usingAnnotation)
            return conf;

        AnnotationConfiguration aconf = (AnnotationConfiguration) conf;

        PersistenceUnitSelection selection = getPersistenceUnitSelection();
        Collection<Class<?>> persistentClasses = selection.mergePersistentClasses();

        for (Class<?> persistentClass : persistentClasses)
            aconf.addClass(persistentClass);

        return aconf;
    }

    @Override
    protected SessionFactory buildSessionFactory()
            throws Exception {
        return new LazyInitSessionFactory() {

            private static final long serialVersionUID = 1L;

            @Override
            protected SessionFactory newSessionFactory()
                    throws Exception {

                lazyConfigure();

                return buildSessionFactoryImpl();
            }
        };
    }

    private SessionFactory buildSessionFactoryImpl()
            throws Exception {
        return super.buildSessionFactory();
    }

}
