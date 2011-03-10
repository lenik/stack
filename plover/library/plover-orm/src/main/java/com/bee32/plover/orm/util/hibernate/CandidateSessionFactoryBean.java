package com.bee32.plover.orm.util.hibernate;

import java.util.List;
import java.util.Properties;

import org.hibernate.transaction.JDBCTransactionFactory;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

import com.bee32.plover.orm.unit.PersistenceUnitSelection;

public abstract class CandidateSessionFactoryBean
        extends LocalSessionFactoryBean {

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
        properties.setProperty("hibernate.transaction.factory_class", JDBCTransactionFactory.class.getName());
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
        // SelectedSessionFactory

        Properties hibernateProperties = new Properties();
        populateHibernateProperties(hibernateProperties);
        this.setHibernateProperties(hibernateProperties);

        // Merge mapping resources
        List<String> allResources = getPersistenceUnitSelection().mergeMappingResources();

        if (!allResources.isEmpty()) {
            String[] resourceArray = allResources.toArray(new String[0]);
            this.setMappingResources(resourceArray);
        }

        super.afterPropertiesSet();
    }

}
