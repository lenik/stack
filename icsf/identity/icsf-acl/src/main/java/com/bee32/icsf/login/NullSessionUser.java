package com.bee32.icsf.login;

import com.bee32.icsf.principal.User;
import com.bee32.icsf.principal.UserDto;

public class NullSessionUser
        extends SessionUser {

    private static final long serialVersionUID = 1L;

    @Override
    public User getInternalUserOpt() {
        return null;
    }

    @Override
    public UserDto getUserOpt() {
        return null;
    }

    public static final NullSessionUser INSTANCE = new NullSessionUser();

}
