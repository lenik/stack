package com.bee32.ape.engine.identity.composite;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.impl.persistence.entity.MembershipEntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompositeMembershipEntityManager
        extends MembershipEntityManager {

    static final Logger logger = LoggerFactory.getLogger(CompositeMembershipEntityManager.class);

    private List<MembershipEntityManager> implementations = new ArrayList<>();
    private MembershipEntityManager master;

    public CompositeMembershipEntityManager() {
    }

    public void addImplementation(MembershipEntityManager implementation, boolean master) {
        if (implementation == null)
            throw new NullPointerException("implementation");
        implementations.add(implementation);
        if (master)
            this.master = implementation;
    }

    public MembershipEntityManager getMaster() {
        if (master == null)
            throw new IllegalStateException("master implementation isn't set.");
        return master;
    }

    @Override
    public void createMembership(String userId, String groupId) {
        getMaster().createMembership(userId, groupId);
    }

    @Override
    public void deleteMembership(String userId, String groupId) {
        for (MembershipEntityManager impl : implementations) {
            impl.deleteMembership(userId, groupId);
        }
    }

}
