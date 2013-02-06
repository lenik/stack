package com.bee32.ape.engine.identity;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;

import com.bee32.ape.engine.identity.composite.CompositeUserEntityManager;
import com.bee32.ape.engine.identity.icsf.IcsfUserEntityManager;
import com.bee32.ape.engine.identity.mem.InMemoryUserEntityManager;
import com.bee32.ape.engine.identity.mem.MemoryActivitiDatabase;

public class ApeUserEntityManagerFactory
        implements SessionFactory {

    CompositeUserEntityManager manager;

    public ApeUserEntityManagerFactory(MemoryActivitiDatabase database) {
        if (database == null)
            throw new NullPointerException("database");

        manager = new CompositeUserEntityManager();
        manager.addImplementation(new InMemoryUserEntityManager(database), false);
        manager.addImplementation(new IcsfUserEntityManager(), true);
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
