package com.bee32.plover.orm.util.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

import com.bee32.plover.orm.unit.IPersistenceUnit;

public abstract class SessionFactoryBuilder {

    private final Map<String, String> properties;

    public SessionFactoryBuilder() {
        properties = new TreeMap<String, String>();
    }

    public String getProperty(Object key) {
        return properties.get(key);
    }

    public String setProperty(String key, String value) {
        return properties.put(key, value);
    }

    public SessionFactory buildForUnits(IPersistenceUnit... persistenceUnits) {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();

        // Inject custom properties.
        Properties hibernateProperties = new Properties();
        doConfigure(hibernateProperties);
        sessionFactoryBean.setHibernateProperties(hibernateProperties);

        // Merge mapping resources
        List<String> allResources = new ArrayList<String>();
        for (IPersistenceUnit persistenceUnit : persistenceUnits) {
            String[] resources = persistenceUnit.getMappingResources();
            for (String resource : resources)
                allResources.add(resource);
        }
        if (!allResources.isEmpty()) {
            String[] resourceArray = allResources.toArray(new String[0]);
            sessionFactoryBean.setMappingResources(resourceArray);
        }

        // Reduce & return.
        try {
            sessionFactoryBean.afterPropertiesSet();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        return sessionFactoryBean.getObject();
    }

    protected void doConfigure(Properties properties) {
        properties.putAll(this.properties);
    }

}
