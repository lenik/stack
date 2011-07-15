package com.bee32.sem.people.web;

import java.util.List;

import javax.annotation.PostConstruct;

import org.hibernate.criterion.Restrictions;
import org.primefaces.model.TreeNode;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.bee32.icsf.login.UserPassword;
import com.bee32.icsf.principal.Group;
import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.User;
import com.bee32.icsf.principal.dto.GroupDto;
import com.bee32.icsf.principal.dto.RoleDto;
import com.bee32.icsf.principal.dto.UserDto;

@Component
@Scope("view")
public class UserAdminBean extends PrincipalAdminBean {

    private static final long serialVersionUID = 1L;


    private UserDto user;

    private String password;
    private String passwordConfirm;


    private RoleDto selectedRole;
    private TreeNode selectedRoleNode;

    private GroupDto selectedGroup;
    private TreeNode selectedGroupNode;


    public UserDto getUser() {
        if (user == null) {
            _newUser();
        }
        return user;
    }

    public void setUser(UserDto user) {
        this.user = reload(user);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
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

    public List<RoleDto> getRoles() {
        return user.getAssignedRoles();
    }

    public List<GroupDto> getGroups() {
        return user.getAssignedGroups();
    }




    @PostConstruct
    public void init() {
	loadRoleTree();
	loadGroupTree();
	}

	private void _newUser() {
		user = new UserDto();
	}

	public void doNewUser() {
		editNewStatus = true;
		_newUser();
	}

	public void doModifyUser() {
		editNewStatus = false;
	}


    public void doSave() {
	if(editNewStatus) {
		//新增
	        Principal existing = serviceFor(Principal.class).get(user.getId());
	        if (existing != null) {
	            uiLogger.error("保存失败:用户已存在。");
	            return;
	        }
	}

        if(!password.equals(passwordConfirm)) {
            uiLogger.error("密码和密码确认不匹配");
            return;
        }

        User u = user.unmarshal(this);


        try {
            serviceFor(User.class).saveOrUpdate(u);

            UserPassword pass = new UserPassword(u, password);
            serviceFor(UserPassword.class).save(pass);
            password = "";
            passwordConfirm = "";

            uiLogger.info("保存成功。");
        } catch (Exception e) {
            uiLogger.error("保存失败.错误消息:" + e.getMessage());
        }
    }

    public void doDelete() {
		try {
		    serviceFor(UserPassword.class).deleteAll(Restrictions.eq("user.id", user.getId()));

			serviceFor(User.class).delete(user.unmarshal());
			uiLogger.info("删除成功!");
		} catch (DataIntegrityViolationException e) {
			uiLogger.error("删除失败,违反约束归则,可能你需要删除的用户在其它地方被使用到!");
		}
    }


    public void doAddRole() {
        user = reload(user);
        user.addAssignedRole((RoleDto) selectedRoleNode.getData());
        serviceFor(User.class).saveOrUpdate(user.unmarshal());
    }

    public void doRemoveRole() {
        user = reload(user);
        user.removeAssignedRole(selectedRole);
        serviceFor(User.class).saveOrUpdate(user.unmarshal());
    }

    public void doAddGroup() {
        GroupDto group = (GroupDto) selectedGroupNode.getData();
        group = reload(group);
        user = reload(user);
        group.addMemberUser(user);
        serviceFor(Group.class).saveOrUpdate(group.unmarshal());
        user.addAssignedGroup((GroupDto) selectedGroupNode.getData());
    }

    public void doRemoveGroup() {
        selectedGroup = reload(selectedGroup);
        selectedGroup.removeMemberUser(user);
        serviceFor(Group.class).saveOrUpdate(selectedGroup.unmarshal());
        user.removeAssignedGroup(selectedGroup);
    }

}
