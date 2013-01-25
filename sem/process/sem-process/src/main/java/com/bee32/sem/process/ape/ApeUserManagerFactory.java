package com.bee32.sem.process.ape;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.UserManager;

public class ApeUserManagerFactory
        implements SessionFactory {

    @Override
    public Class<?> getSessionType() {
        return UserManager.class;
    }

    @Override
    public Session openSession() {
        return new ApeUserManager();
    }

}
