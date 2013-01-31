package com.bee32.ape.engine.identity;

import org.activiti.engine.impl.persistence.entity.MembershipEntityManager;

import com.bee32.ape.engine.base.IApeActivitiAdapter;
import com.bee32.icsf.principal.Role;
import com.bee32.icsf.principal.User;

public class ApeMembershipEntityManager
        extends MembershipEntityManager
        implements IApeActivitiAdapter {

    @Override
    public void createMembership(String userId, String groupId) {
        String icsfUserName = userId;
        String icsfGroupName = groupId + GROUP_EXT;

        User _user = ctx.data.access(icsfUserType).getByName(icsfUserName);
        Role _group = ctx.data.access(icsfGroupType).getByName(icsfGroupName);

        _user.addAssignedRole(_group);

        ctx.data.access(icsfUserType).update(_user);
    }

    @Override
    public void deleteMembership(String userId, String groupId) {
        String icsfUserName = userId;
        String icsfGroupName = groupId + GROUP_EXT;

        User _user = ctx.data.access(icsfUserType).getByName(icsfUserName);
        Role _group = ctx.data.access(icsfGroupType).getByName(icsfGroupName);

        _user.removeAssignedRole(_group);

        ctx.data.access(icsfUserType).update(_user);
    }

}
