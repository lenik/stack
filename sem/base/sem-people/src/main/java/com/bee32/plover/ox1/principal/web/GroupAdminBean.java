package com.bee32.plover.ox1.principal.web;

import com.bee32.icsf.principal.Group;
import com.bee32.icsf.principal.GroupDto;
import com.bee32.icsf.principal.PrincipalCheckException;
import com.bee32.icsf.principal.PrincipalCriteria;
import com.bee32.icsf.principal.PrincipalDiag;
import com.bee32.icsf.principal.RoleDto;
import com.bee32.icsf.principal.UserDto;
import com.bee32.plover.restful.resource.StandardViews;
import com.bee32.sem.misc.SimpleTreeEntityViewBean;
import com.bee32.sem.misc.UnmarshalMap;

public class GroupAdminBean
        extends SimpleTreeEntityViewBean {

    private static final long serialVersionUID = 1L;

    RoleDto selectedRole;
    UserDto selectedUser;

    public GroupAdminBean() {
        super(Group.class, GroupDto.class, 0);
    }

    @Override
    protected boolean preUpdate(UnmarshalMap uMap)
            throws Exception {
        for (Group _group : uMap.<Group> entitySet()) {
            if (StandardViews.CREATE_FORM.equals(getCurrentView())) {
                String name = _group.getName();
                if (name != null) {
                    Group existing = ctx.data.access(Group.class).getByName(name);
                    if (existing != null) {
                        uiLogger.error("组已存在: " + name);
                        return false;
                    }
                }
            }
            try {
                PrincipalDiag.checkDeadLoop(_group);
            } catch (PrincipalCheckException e) {
                uiLogger.error("组结构非法: 组存在循环引用", e);
                return false;
            }
        }
        return true;
    }

    public RoleDto getSelectedRole() {
        return selectedRole;
    }

    public void setSelectedRole(RoleDto selectedRole) {
        this.selectedRole = selectedRole;
    }

    public void addRole() {
        GroupDto group = getOpenedObject();
        group.addAssignedRole(selectedRole);
    }

    public void removeRole() {
        if (selectedRole != null) {
            GroupDto group = getOpenedObject();
            group.removeAssignedRole(selectedRole);
        }
    }

    public UserDto getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(UserDto selectedUser) {
        this.selectedUser = selectedUser;
    }

    public void addUser() {
        GroupDto group = getOpenedObject();
        group.addMemberUser(selectedUser);
    }

    public void removeUser() {
        if (selectedUser != null) {
            GroupDto group = getOpenedObject();
            group.removeMemberUser(selectedUser);
        }
    }

    @Override
    public void addNameOrLabelRestriction() {
        addSearchFragment("名称含有 " + searchPattern, PrincipalCriteria.namedLike(searchPattern));
        searchPattern = null;
    }

}
