package com.bee32.icsf.principal.dto;

import com.bee32.icsf.principal.Role;

public class RoleDto
        extends AbstractRoleDto<Role> {

    private static final long serialVersionUID = 1L;

    public RoleDto() {
        super();
    }

    public RoleDto(Role source) {
        super(source);
    }

    public RoleDto(int selection) {
        super(selection);
    }

    public RoleDto(int selection, Role source) {
        super(selection, source);
    }

}
