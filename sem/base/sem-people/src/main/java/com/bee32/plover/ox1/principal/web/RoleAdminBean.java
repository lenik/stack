package com.bee32.plover.ox1.principal.web;

import com.bee32.icsf.principal.GroupDto;
import com.bee32.icsf.principal.PrincipalCheckException;
import com.bee32.icsf.principal.PrincipalCriteria;
import com.bee32.icsf.principal.PrincipalDiag;
import com.bee32.icsf.principal.Role;
import com.bee32.icsf.principal.RoleDto;
import com.bee32.icsf.principal.UserDto;
import com.bee32.plover.restful.resource.StandardViews;
import com.bee32.sem.misc.SimpleTreeEntityViewBean;
import com.bee32.sem.misc.UnmarshalMap;

public class RoleAdminBean
        extends SimpleTreeEntityViewBean {

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
            RoleDto role = uMap.getSourceDto(_role);
            if (currentView.equals(StandardViews.CREATE_FORM)) {
                Role existing = serviceFor(Role.class).getByName(role.getName());
                if (existing != null) {
                    uiLogger.error("保存失败: 角色已存在: " + role.getName());
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
        RoleDto role = getActiveObject();
        role.addResponsibleGroup(selectedGroup);
    }

    public void removeGroup() {
        RoleDto role = getActiveObject();
        role.removeResponsibleGroup(selectedGroup);
    }

    public UserDto getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(UserDto selectedUser) {
        this.selectedUser = selectedUser;
    }

    public void addUser() {
        RoleDto role = getActiveObject();
        role.addResponsibleUser(selectedUser);
    }

    public void removeUser() {
        RoleDto role = getActiveObject();
        role.removeResponsibleUser(selectedUser);
    }

    @Override
    public void addNameOrLabelRestriction() {
        addSearchFragment("名称含有 " + searchPattern, PrincipalCriteria.namedLike(searchPattern));
        searchPattern = null;
    }

}
