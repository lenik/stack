package com.bee32.plover.ox1.principal.web;

import com.bee32.icsf.principal.*;
import com.bee32.plover.restful.resource.StandardViews;
import com.bee32.sem.misc.TreeEntityViewBean;
import com.bee32.sem.misc.UnmarshalMap;

public class RoleAdminBean
        extends TreeEntityViewBean {

    private static final long serialVersionUID = 1L;

    GroupDto selectedGroup;
    UserDto selectedUser;

    public RoleAdminBean() {
        super(Role.class, RoleDto.class, 0);
    }

    @Override
    protected boolean preUpdate(UnmarshalMap uMap)
            throws Exception {
        for (Role _role : uMap.<Role> entitySet()) {
            if (StandardViews.CREATE_FORM.equals(getCurrentView())) {
                Role existing = DATA(Role.class).getByName(_role.getName());
                if (existing != null) {
                    uiLogger.error("角色已存在: " + _role.getName());
                    return false;
                }
            }
            try {
                PrincipalDiag.checkDeadLoop(_role);
            } catch (PrincipalCheckException e) {
                uiLogger.error("角色结构非法: 角色存在循环引用", e);
                return false;
            }
        }
        return true;
    }

    public GroupDto getSelectedGroup() {
        return selectedGroup;
    }

    public void setSelectedGroup(GroupDto selectedGroup) {
        this.selectedGroup = selectedGroup;
    }

    public void addGroup() {
        RoleDto role = getOpenedObject();
        role.addResponsibleGroup(selectedGroup);
    }

    public void removeGroup() {
        RoleDto role = getOpenedObject();
        role.removeResponsibleGroup(selectedGroup);
    }

    public UserDto getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(UserDto selectedUser) {
        this.selectedUser = selectedUser;
    }

    public void addUser() {
        RoleDto role = getOpenedObject();
        role.addResponsibleUser(selectedUser);
    }

    public void removeUser() {
        RoleDto role = getOpenedObject();
        role.removeResponsibleUser(selectedUser);
    }

    @Override
    public void addNameOrLabelRestriction() {
        setSearchFragment("name", "名称含有 " + searchPattern, PrincipalCriteria.namedLike(searchPattern));
        searchPattern = null;
    }

}
