package com.bee32.sem.people.web;

import java.util.List;

import javax.annotation.PostConstruct;

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

	private void loadRoleTree() {
		root = new DefaultTreeNode("根", null);

		List<Role> roles = serviceFor(Role.class).list(Restrictions.isNull("parent"));	//get top roles
        List<RoleDto> roleDtos = DTOs.marshalList(RoleDto.class, roles);
        for(RoleDto roleDto : roleDtos) {
		TreeNode roleNode = new DefaultTreeNode(roleDto, root);

//        	if(roleDto) {
//
//        	}
        }
	}

	@PostConstruct
	public void init() {
		loadRoleTree();
	}


	public TreeNode getRoot() {
		return root;
	}

}
