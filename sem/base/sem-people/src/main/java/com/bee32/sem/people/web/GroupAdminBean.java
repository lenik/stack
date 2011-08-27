package com.bee32.sem.people.web;

import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.model.TreeNode;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.bee32.plover.ox1.principal.Group;
import com.bee32.plover.ox1.principal.GroupDto;
import com.bee32.plover.ox1.principal.Principal;
import com.bee32.plover.ox1.principal.PrincipalCheckException;
import com.bee32.plover.ox1.principal.PrincipalDiag;
import com.bee32.plover.ox1.principal.RoleDto;
import com.bee32.plover.ox1.principal.UserDto;

@Component
@Scope("view")
public class GroupAdminBean extends PrincipalAdminBean {

    private static final long serialVersionUID = 1L;


    private GroupDto group;
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

	public void doModifyGroup() {
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
            uiLogger.error("保存失败.错误消息:" + e.getMessage());
        }
    }

    public void doDelete() {
		try {
			serviceFor(Group.class).delete(group.unmarshal());
			loadGroupTree();
			uiLogger.info("删除成功!");
		} catch (DataIntegrityViolationException e) {
			uiLogger.error("删除失败,违反约束归则,可能你需要删除的组在其它地方被使用到!");
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
    }
}
