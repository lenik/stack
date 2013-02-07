package com.bee32.ape.engine.identity;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.MembershipEntityManager;

import com.bee32.ape.engine.identity.composite.CompositeMembershipEntityManager;
import com.bee32.ape.engine.identity.icsf.IIcsfTypeMapping;
import com.bee32.ape.engine.identity.icsf.IcsfMembershipEntityManager;
import com.bee32.ape.engine.identity.mem.MemoryActivitiDatabase;

public class ApeMembershipEntityManagerFactory
        implements SessionFactory {

    CompositeMembershipEntityManager manager;

    public ApeMembershipEntityManagerFactory(IIcsfTypeMapping icsfTypeMapping, MemoryActivitiDatabase database) {
        if (icsfTypeMapping == null)
            throw new NullPointerException("icsfTypeMapping");
        if (database == null)
            throw new NullPointerException("database");

        manager = new CompositeMembershipEntityManager();
        // manager.addImplementation(new InMemoryMembershipEntityManager(database), false);
        manager.addImplementation(new IcsfMembershipEntityManager(icsfTypeMapping), true);
    }

    @Override
    public Class<?> getSessionType() {
        return MembershipEntityManager.class;
    }

    @Override
    public Session openSession() {
        return manager;
    }

}
