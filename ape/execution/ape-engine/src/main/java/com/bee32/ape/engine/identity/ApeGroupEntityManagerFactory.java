package com.bee32.ape.engine.identity;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;

public class ApeGroupEntityManagerFactory
        implements SessionFactory {

    @Override
    public Class<?> getSessionType() {
        return GroupEntityManager.class;
    }

    @Override
    public Session openSession() {
        return new ApeGroupManager();
    }

}
