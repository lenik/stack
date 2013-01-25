package com.bee32.sem.process.ape;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.GroupManager;

public class ApeGroupManagerFactory
        implements SessionFactory {

    @Override
    public Class<?> getSessionType() {
        return GroupManager.class;
    }

    @Override
    public Session openSession() {
        return new ApeGroupManager();
    }

}
