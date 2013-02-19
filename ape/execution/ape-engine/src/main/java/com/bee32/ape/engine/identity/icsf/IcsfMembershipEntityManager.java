package com.bee32.ape.engine.identity.icsf;

import org.activiti.engine.impl.persistence.entity.MembershipEntityManager;

import com.bee32.ape.engine.base.IApeActivitiAdapter;
import com.bee32.icsf.principal.Group;
import com.bee32.icsf.principal.UserService;

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

        UserService userService = ctx.bean.getBean(UserService.class);
        if (icsfTypeMapping.getIcsfGroupType() == Group.class)
            userService.assignGroupByName(icsfUserName, icsfGroupName);
        else
            userService.assignRoleByName(icsfUserName, icsfGroupName);
    }

    @Override
    public void deleteMembership(String userId, String groupId) {
        String icsfUserName = userId;
        String icsfGroupName = icsfTypeMapping.toIcsfGroupName(groupId);

        UserService userService = ctx.bean.getBean(UserService.class);
        if (icsfTypeMapping.getIcsfGroupType() == Group.class)
            userService.unassignGroupByName(icsfUserName, icsfGroupName);
        else
            userService.unassignRoleByName(icsfUserName, icsfGroupName);
    }

}
