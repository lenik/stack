package com.bee32.ape.engine.identity;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.MembershipEntityManager;

import com.bee32.ape.engine.identity.icsf.IcsfMembershipEntityManager;

public class ApeMembershipEntityManagerFactory
        implements SessionFactory {

    @Override
    public Class<?> getSessionType() {
        return MembershipEntityManager.class;
    }

    @Override
    public Session openSession() {
        return new IcsfMembershipEntityManager();
    }

}
