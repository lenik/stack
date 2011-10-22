package com.bee32.sem.people.web;

import java.util.List;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.plover.ox1.principal.Group;
import com.bee32.plover.ox1.principal.GroupDto;
import com.bee32.plover.ox1.principal.Role;
import com.bee32.plover.ox1.principal.RoleDto;
import com.bee32.plover.ox1.principal.User;
import com.bee32.plover.ox1.principal.UserDto;
import com.bee32.plover.ox1.tree.TreeCriteria;

public class PrincipalAdminBean
        extends EntityViewBean {

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

        List<Role> rootRoles = serviceFor(Role.class).list(TreeCriteria.root());
        List<RoleDto> rootRoleDtos = DTOs.mrefList(RoleDto.class, -1, rootRoles);

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

        List<Group> rootGroups = serviceFor(Group.class).list(TreeCriteria.root());
        List<GroupDto> rootGroupDtos = DTOs.mrefList(GroupDto.class, -1, rootGroups);

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
