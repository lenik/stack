package com.bee32.ape.engine.identity.icsf;

import org.activiti.engine.impl.persistence.entity.MembershipEntityManager;

import com.bee32.ape.engine.base.IApeActivitiAdapter;
import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.User;

public class IcsfMembershipEntityManager
        extends MembershipEntityManager
        implements IApeActivitiAdapter {

    private IIcsfTypeMapping icsfTypeMapping;

    public IcsfMembershipEntityManager(IIcsfTypeMapping icsfTypeMapping) {
        if (icsfTypeMapping == null)
            throw new NullPointerException("icsfTypeMapping");
        this.icsfTypeMapping = icsfTypeMapping;
    }

    @Override
    public void createMembership(String userId, String groupId) {
        String icsfUserName = userId;
        String icsfGroupName = icsfTypeMapping.toIcsfGroupName(groupId);

        User _user = ctx.data.access(icsfUserType).getByName(icsfUserName);
        Principal _group = ctx.data.access(icsfTypeMapping.getIcsfGroupType()).getByName(icsfGroupName);

        icsfTypeMapping.addMembership(_user, _group);

        ctx.data.access(icsfUserType).update(_user);
        // ctx.data.access(*).saveOrUpdateAll(_user, _group);
    }

    @Override
    public void deleteMembership(String userId, String groupId) {
        String icsfUserName = userId;
        String icsfGroupName = icsfTypeMapping.toIcsfGroupName(groupId);

        User _user = ctx.data.access(icsfUserType).getByName(icsfUserName);
        Principal _group = ctx.data.access(icsfTypeMapping.getIcsfGroupType()).getByName(icsfGroupName);

        icsfTypeMapping.removeMembership(_user, _group);

        ctx.data.access(icsfUserType).update(_user);
        // ctx.data.access(*).saveOrUpdateAll(_user, _group);
    }

}
