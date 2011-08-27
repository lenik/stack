package com.bee32.icsf.login;

import com.bee32.plover.ox1.principal.User;

public class NullLoginInfo
        extends LoginInfo {

    private static final long serialVersionUID = 1L;

    @Override
    public User getUser() {
        return null;
    }

    public static final NullLoginInfo INSTANCE = new NullLoginInfo();

}
