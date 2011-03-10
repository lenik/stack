package com.bee32.plover.orm.util.hibernate;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.orm.hibernate3.AbstractSessionFactoryBean;
import org.springframework.stereotype.Service;

@Service
public class PreferredSessionFactoryBean
        implements FactoryBean<SessionFactory> {

    private static final long serialVersionUID = 1L;

    private static String preferredName;
    static Map<String, AbstractSessionFactoryBean> candidates = new HashMap<String, AbstractSessionFactoryBean>();

    public static String getPreferredName() {
        return preferredName;
    }

    public static void setPreferredName(String preferredName) {
        if (preferredName == null)
            throw new NullPointerException("preferredName");
        PreferredSessionFactoryBean.preferredName = preferredName;
    }

    public static void register(String name, AbstractSessionFactoryBean sessionFactoryBean) {
        if (candidates.containsKey(name))
            throw new IllegalArgumentException("Candidate session factory is already existed: " + name);

        candidates.put(name, sessionFactoryBean);
    }

    public static void unregister(String name) {
        candidates.remove(name);
    }

    @Override
    public SessionFactory getObject()
            throws Exception {
        AbstractSessionFactoryBean preferredSessionFactoryBean = candidates.get(preferredName);

        if (preferredSessionFactoryBean == null)
            throw new IllegalStateException("The preferred session factory isn't existed: " + preferredName);

        return preferredSessionFactoryBean.getObject();
    }

    @Override
    public Class<?> getObjectType() {
        return SessionFactory.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
