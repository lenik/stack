package com.bee32.plover.orm.util.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;

import org.hibernate.transaction.JDBCTransactionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.plover.orm.unit.IPersistenceUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.plover.orm.util.PUSelection;

@ComponentTemplate
public abstract class CandidateSessionFactoryBean
        implements InitializingBean, BeanFactoryPostProcessor {

    private final String name;

    @Inject
    private ApplicationContext context;

    public CandidateSessionFactoryBean(String preferenceName) {
        if (preferenceName == null)
            throw new NullPointerException("preferenceName");

        this.name = preferenceName;

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
            throws BeansException {
    }

    @Override
    public void afterPropertiesSet()
            throws Exception {

        AutowireCapableBeanFactory acbf = context.getAutowireCapableBeanFactory();

        LocalSessionFactoryBean sfb = acbf.createBean(LocalSessionFactoryBean.class);

        // SelectedSessionFactory

        Properties hibernateProperties = new Properties();
        populateHibernateProperties(hibernateProperties);
        sfb.setHibernateProperties(hibernateProperties);

        // Merge mapping resources
        PersistenceUnit[] persistenceUnits = getPersistenceUnits();

        List<String> allResources = new ArrayList<String>();
        for (IPersistenceUnit persistenceUnit : persistenceUnits) {
            if (persistenceUnit == null)
                throw new NullPointerException("persistenceUnit");

            String[] mappingResources = persistenceUnit.getMappingResources();

            for (String resource : mappingResources)
                allResources.add(resource);
        }
        if (!allResources.isEmpty()) {
            String[] resourceArray = allResources.toArray(new String[0]);
            sfb.setMappingResources(resourceArray);
        }

        PreferredSessionFactoryBean.register(name, sfb);
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
    protected PersistenceUnit[] getPersistenceUnits() {
        return PUSelection.getPersistenceUnits();
    }

}
