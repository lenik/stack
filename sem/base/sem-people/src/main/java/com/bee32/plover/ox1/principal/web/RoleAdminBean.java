package com.bee32.plover.ox1.principal.web;

import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.model.TreeNode;

import com.bee32.icsf.principal.Group;
import com.bee32.icsf.principal.GroupDto;
import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.PrincipalCheckException;
import com.bee32.icsf.principal.PrincipalDiag;
import com.bee32.icsf.principal.Role;
import com.bee32.icsf.principal.RoleDto;
import com.bee32.icsf.principal.User;
import com.bee32.icsf.principal.UserDto;

public class RoleAdminBean extends PrincipalAdminBean {

    private static final long serialVersionUID = 1L;


    private RoleDto role;
    private TreeNode selectedNode;
    private TreeNode selectedInheritedRoleNode;

    private GroupDto selectedGroup;
    private TreeNode selectedGroupNode;

    private UserDto selectedUser;
    private UserDto selectedUserToAdd;


    public RoleDto getRole() {
        if (role == null) {
            _newRole();
        }
        return role;
    }

    public void setRole(RoleDto role) {
        this.role = reload(role);
    }

    public TreeNode getSelectedInheritedRoleNode() {
		return selectedInheritedRoleNode;
	}

	public void setSelectedInheritedRoleNode(TreeNode selectedInheritedRoleNode) {
		this.selectedInheritedRoleNode = selectedInheritedRoleNode;
	}


    public GroupDto getSelectedGroup() {
        return selectedGroup;
    }

    public void setSelectedGroup(GroupDto selectedGroup) {
        this.selectedGroup = selectedGroup;
    }

    public TreeNode getSelectedGroupNode() {
        return selectedGroupNode;
    }

    public void setSelectedGroupNode(TreeNode selectedGroupNode) {
        this.selectedGroupNode = selectedGroupNode;
    }

    public List<GroupDto> getGroups() {
        return role.getResponsibleGroups();
    }

    public List<UserDto> getUsers() {
        return role.getResponsibleUsers();
    }


    public UserDto getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(UserDto selectedUser) {
        this.selectedUser = selectedUser;
    }

    public UserDto getSelectedUserToAdd() {
        return selectedUserToAdd;
    }

    public void setSelectedUserToAdd(UserDto selectedUserToAdd) {
        this.selectedUserToAdd = selectedUserToAdd;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }





    @PostConstruct
    public void init() {
	loadRoleTree();
	loadGroupTree();
	}

	private void _newRole() {
		role = new RoleDto().create();
	}

	public void doNewRole() {
		editNewStatus = true;
		_newRole();
	}

    public void chooseRole() {
        role = reload((RoleDto) selectedNode.getData());
    }

	public void doModifyRole() {
	    chooseRole();
		editNewStatus = false;
	}


    public void doSave() {
	if(editNewStatus) {
		//新增
	        Principal existing = serviceFor(Principal.class).getByName(role.getName());
	        if (existing != null) {
	            uiLogger.error("保存失败:角色已存在。");
	            return;
	        }
	}

        Role r = role.unmarshal(this);

        try {
            PrincipalDiag.checkDeadLoop(r);
        } catch (PrincipalCheckException e) {
            uiLogger.error("角色结构非法:角色存在循环引用", e);
            return;
        }

        try {
            serviceFor(Role.class).saveOrUpdate(r);
            loadRoleTree();
            uiLogger.info("保存成功。");
        } catch (Exception e) {
            uiLogger.error("保存失败.", e);
        }
    }

    public void doDelete() {
        if(role.getDerivedRoles().size() > 0) {
            uiLogger.info("本角色有下属角色，请先删除下属角色!");
            return;
        }

		try {
		    Role _role = role.unmarshal();
            if (_role.getParent() != null) {
                _role.getParent().removeChild(_role);
                _role.setParent(null);
            }
			serviceFor(Role.class).delete(_role);
			loadRoleTree();
			uiLogger.info("删除成功!");
		} catch (Exception e) {
			uiLogger.error("删除失败.", e);
		}
    }

    public void doSelectInheritedRole() {
	role.setInheritedRole((RoleDto) selectedInheritedRoleNode.getData());
    }

    public void doAddGroup() {
        GroupDto group = (GroupDto)selectedGroupNode.getData();
        group = reload(group);
        group.addAssignedRole(role);
        serviceFor(Group.class).saveOrUpdate(group.unmarshal());
        role.addResponsibleGroup(group);
    }

    public void doRemoveGroup() {
        selectedGroup = reload(selectedGroup);
        selectedGroup.removeAssignedRole(role);
        serviceFor(Group.class).saveOrUpdate(selectedGroup.unmarshal());
        role.removeResponsibleGroup(selectedGroup);
        selectedGroup = null;
    }


    public void doAddUser() {
        selectedUserToAdd = reload(selectedUserToAdd);
        selectedUserToAdd.addAssignedRole(role);
        serviceFor(User.class).saveOrUpdate(selectedUserToAdd.unmarshal());
        role.addResponsibleUser(selectedUserToAdd);
    }

    public void doRemoveUser() {
        selectedUser = reload(selectedUser);
        selectedUser.removeAssignedRole(role);
        serviceFor(User.class).saveOrUpdate(selectedUser.unmarshal());
        role.removeResponsibleUser(selectedUser);
        selectedUser = null;
    }
}
