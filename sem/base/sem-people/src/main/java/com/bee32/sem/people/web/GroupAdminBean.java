package com.bee32.sem.people.web;

import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.model.TreeNode;

import com.bee32.plover.ox1.principal.Group;
import com.bee32.plover.ox1.principal.GroupDto;
import com.bee32.plover.ox1.principal.Principal;
import com.bee32.plover.ox1.principal.PrincipalCheckException;
import com.bee32.plover.ox1.principal.PrincipalDiag;
import com.bee32.plover.ox1.principal.RoleDto;
import com.bee32.plover.ox1.principal.UserDto;

public class GroupAdminBean extends PrincipalAdminBean {

    private static final long serialVersionUID = 1L;


    private GroupDto group;
    private TreeNode selectedNode;
    private TreeNode selectedInheritedGroupNode;

    private RoleDto selectedRole;
    private TreeNode selectedRoleNode;

    private UserDto selectedUser;
    private UserDto selectedUserToAdd;


    public GroupDto getGroup() {
        if (group == null) {
            _newGroup();
        }
        return group;
    }

    public void setGroup(GroupDto group) {
        this.group = reload(group);
    }

    public TreeNode getSelectedInheritedGroupNode() {
        return selectedInheritedGroupNode;
    }

    public void setSelectedInheritedGroupNode(TreeNode selectedInheritedGroupNode) {
        this.selectedInheritedGroupNode = selectedInheritedGroupNode;
    }

    public RoleDto getSelectedRole() {
        return selectedRole;
    }

    public void setSelectedRole(RoleDto selectedRole) {
        this.selectedRole = selectedRole;
    }

    public TreeNode getSelectedRoleNode() {
        return selectedRoleNode;
    }

    public void setSelectedRoleNode(TreeNode selectedRoleNode) {
        this.selectedRoleNode = selectedRoleNode;
    }

    public List<RoleDto> getRoles() {
        return group.getAssignedRoles();
    }

    public List<UserDto> getUsers() {
        return group.getMemberUsers();
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

	private void _newGroup() {
		group = new GroupDto().create();
	}

	public void doNewGroup() {
		editNewStatus = true;
		_newGroup();
	}

	public void chooseGroup() {
	    group = reload((GroupDto) selectedNode.getData());
	}

	public void doModifyGroup() {
		chooseGroup();
	    editNewStatus = false;
	}


    public void doSave() {
	if(editNewStatus) {
		//新增
	        Principal existing = serviceFor(Principal.class).getByName(group.getName());
	        if (existing != null) {
	            uiLogger.error("保存失败:组已存在。");
	            return;
	        }
	}

        Group g = group.unmarshal(this);

        try {
            PrincipalDiag.checkDeadLoop(g);
        } catch (PrincipalCheckException e) {
            uiLogger.error("组结构非法:组存在循环引用", e);
            return;
        }

        try {
            serviceFor(Group.class).saveOrUpdate(g);
            loadGroupTree();
            uiLogger.info("保存成功。");
        } catch (Exception e) {
            uiLogger.error("保存失败.", e);
        }
    }

    public void doDelete() {
        if(group.getDerivedGroups().size() > 0) {
            uiLogger.info("本组有下属组，请先删除下属组!");
            return;
        }

		try {
		    Group _group = group.unmarshal();
            if (_group.getParent() != null) {
                _group.getParent().removeChild(_group);
                _group.setParent(null);
            }
			serviceFor(Group.class).delete(_group);
			loadGroupTree();
			uiLogger.info("删除成功!");
		} catch (Exception e) {
			uiLogger.error("删除失败.", e);
		}
    }

    public void doSelectInheritedGroup() {
	group.setInheritedGroup((GroupDto)selectedInheritedGroupNode.getData());
    }

    public void doAddRole() {
        group = reload(group);
        group.addAssignedRole((RoleDto) selectedRoleNode.getData());
        serviceFor(Group.class).saveOrUpdate(group.unmarshal());
    }

    public void doRemoveRole() {
        group = reload(group);
        group.removeAssignedRole(selectedRole);
        serviceFor(Group.class).saveOrUpdate(group.unmarshal());
        selectedRole = null;
    }


    public void doAddUser() {
        selectedUserToAdd = reload(selectedUserToAdd);
        group.addMemberUser(selectedUserToAdd);
        serviceFor(Group.class).saveOrUpdate(group.unmarshal());
    }

    public void doRemoveUser() {
        selectedUser = reload(selectedUser);
        selectedUser.removeAssignedGroup(group);
        group.removeMemberUser(selectedUser);
        serviceFor(Group.class).saveOrUpdate(group.unmarshal());
        selectedUser = null;
    }
}
