package com.bee32.sem.people.web;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.hibernate.criterion.Restrictions;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.icsf.principal.Role;
import com.bee32.icsf.principal.dto.RoleDto;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;

@Component
@Scope("view")
public class RoleAdminBean
        extends EntityViewBean {

	private static final long serialVersionUID = 1L;

	private TreeNode root;
	private RoleDto role;
	private RoleDto selectedRole;
	private TreeNode selectedParentRoleNode;

	public TreeNode getRoot() {
		if(root == null) {
			loadRoleTree();
		}
		return root;
	}

	public RoleDto getRole() {
		if(role == null) {
			_newRole();
		}
		return role;
	}

	public void setRole(RoleDto role) {
		this.role = role;
	}

	public RoleDto getSelectedRole() {
		return selectedRole;
	}

	public void setSelectedRole(RoleDto selectedRole) {
		this.selectedRole = selectedRole;
	}

	public TreeNode getSelectedParentRoleNode() {
		return selectedParentRoleNode;
	}

	public void setSelectedParentRoleNode(TreeNode selectedParentRoleNode) {
		this.selectedParentRoleNode = selectedParentRoleNode;
	}


	private void loadRoleTree() {
		root = new DefaultTreeNode("根", null);

		List<Role> roles = serviceFor(Role.class).list(Restrictions.isNull("inheritedRole"));	//get top roles
        List<RoleDto> roleDtos = DTOs.marshalList(RoleDto.class, roles, true);

        for(RoleDto roleDto : roleDtos) {
		loadRoleRecursive(roleDto, root);
        }
	}

	private void loadRoleRecursive(RoleDto roleDto, TreeNode parentTreeNode) {
		TreeNode roleNode = new DefaultTreeNode(roleDto, parentTreeNode);

	List<RoleDto> subRoles = roleDto.getDerivedRoles();
		for(RoleDto subRole : subRoles) {
			loadRoleRecursive(subRole, roleNode);
		}
	}

	@PostConstruct
	public void init() {

	}

	private void _newRole() {
		role = new RoleDto();
	}

	public void doNewRole() {
		_newRole();
	}

	public void doSave() {
		RoleDto parent = (RoleDto) selectedParentRoleNode.getData();
		role.setInheritedRole(parent);
		Role r = role.unmarshal(this);

		serviceFor(Role.class).saveOrUpdate(r);
		loadRoleTree();

		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "保存成功", "保存成功");
        FacesContext.getCurrentInstance().addMessage(null, message);
	}

}
