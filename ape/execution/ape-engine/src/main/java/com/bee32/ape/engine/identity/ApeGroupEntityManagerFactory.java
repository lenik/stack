package com.bee32.ape.engine.identity;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;

import com.bee32.ape.engine.identity.composite.CompositeGroupEntityManager;
import com.bee32.ape.engine.identity.icsf.IcsfGroupEntityManager;
import com.bee32.ape.engine.identity.mem.InMemoryGroupEntityManager;
import com.bee32.ape.engine.identity.mem.MemoryActivitiDatabase;

public class ApeGroupEntityManagerFactory
        implements SessionFactory {

    CompositeGroupEntityManager manager;

    public ApeGroupEntityManagerFactory(MemoryActivitiDatabase database) {
        if (database == null)
            throw new NullPointerException("database");

        manager = new CompositeGroupEntityManager();
        manager.addImplementation(new InMemoryGroupEntityManager(database), false);
        manager.addImplementation(new IcsfGroupEntityManager(), true);
    }

    @Override
    public Class<?> getSessionType() {
        return GroupEntityManager.class;
    }

    @Override
    public Session openSession() {
        return manager;
    }

}
