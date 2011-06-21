package com.bee32.icsf.principal.dto;

import com.bee32.icsf.principal.Group;

public class GroupDto
        extends AbstractGroupDto<Group> {

    private static final long serialVersionUID = 1L;

    public GroupDto() {
        super();
    }

    public GroupDto(int selection) {
        super(selection);
    }

}
