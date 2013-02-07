package com.bee32.ape.engine.identity;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;

public class ApeUserEntityManagerFactory
        implements SessionFactory {

    ApeUserEntityManager manager;

    public ApeUserEntityManagerFactory(ApeUserEntityManager manager) {
        if (manager == null)
            throw new NullPointerException("manager");
        this.manager = manager;
    }

    @Override
    public Class<?> getSessionType() {
        return UserEntityManager.class;
    }

    @Override
    public Session openSession() {
        return manager;
    }

}
