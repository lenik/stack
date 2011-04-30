package com.bee32.icsf.principal.dto;

import com.bee32.icsf.principal.User;

public class UserDto
        extends AbstractUserDto<User> {

    private static final long serialVersionUID = 1L;

    public UserDto(int selection) {
        super(selection);
    }

    public UserDto(int selection, User source) {
        super(selection, source);
    }

}
