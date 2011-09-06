package com.bee32.icsf.login;

import com.bee32.plover.ox1.principal.User;
import com.bee32.plover.ox1.principal.UserDto;

public class NullLoginInfo
        extends LoginInfo {

    private static final long serialVersionUID = 1L;

    public NullLoginInfo() {
        super(null);
    }

    @Override
    public User getInternalUserOpt() {
        return null;
    }

    @Override
    public UserDto getUserOpt() {
        return null;
    }

    public static final NullLoginInfo INSTANCE = new NullLoginInfo();

}
