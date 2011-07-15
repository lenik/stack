package com.bee32.sem.people.web;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.bee32.icsf.principal.Group;
import com.bee32.icsf.principal.Role;
import com.bee32.icsf.principal.User;
import com.bee32.icsf.principal.dto.GroupDto;
import com.bee32.icsf.principal.dto.RoleDto;
import com.bee32.icsf.principal.dto.UserDto;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;

public class PrincipalAdminBean extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    protected boolean editNewStatus; // true:新增状态;false:修改状态;

    private TreeNode roleRootNode;

    private TreeNode groupRootNode;

    public boolean isEditNewStatus() {
        return editNewStatus;
    }

    public void setEditNewStatus(boolean editNewStatus) {
        this.editNewStatus = editNewStatus;
    }

    public TreeNode getRoleRoot() {
        return roleRootNode;
    }

    public TreeNode getGroupRoot() {
        return groupRootNode;
    }

    protected void loadRoleTree() {
        roleRootNode = new DefaultTreeNode("root", null);

        List<Role> rootRoles = serviceFor(Role.class).list(Restrictions.isNull("parent"));
        List<RoleDto> rootRoleDtos = DTOs.marshalList(RoleDto.class, -1, rootRoles, true);

        for (RoleDto roleDto : rootRoleDtos) {
            loadRoleRecursive(roleDto, roleRootNode);
        }
    }

    private void loadRoleRecursive(RoleDto roleDto, TreeNode parentTreeNode) {
        TreeNode roleNode = new DefaultTreeNode(roleDto, parentTreeNode);

        List<RoleDto> subRoles = roleDto.getDerivedRoles();
        for (RoleDto subRole : subRoles) {
            loadRoleRecursive(subRole, roleNode);
        }
    }

    protected void loadGroupTree() {
        groupRootNode = new DefaultTreeNode("root", null);

        List<Group> rootGroups = serviceFor(Group.class).list(Restrictions.isNull("parent"));
        List<GroupDto> rootGroupDtos = DTOs.marshalList(GroupDto.class, -1, rootGroups, true);

        for (GroupDto groupDto : rootGroupDtos) {
            loadGroupRecursive(groupDto, groupRootNode);
        }
    }

    private void loadGroupRecursive(GroupDto groupDto, TreeNode parentTreeNode) {
        TreeNode groupNode = new DefaultTreeNode(groupDto, parentTreeNode);

        List<GroupDto> subGroups = groupDto.getDerivedGroups();
        for (GroupDto subGroup : subGroups) {
            loadGroupRecursive(subGroup, groupNode);
        }
    }

    public List<UserDto> getAllUser() {
        List<User> allUser = serviceFor(User.class).list();
        return DTOs.marshalList(UserDto.class, allUser);
    }
}
