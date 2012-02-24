package com.bee32.icsf.login;

import javax.inject.Inject;

import com.bee32.icsf.principal.Users;
import com.bee32.plover.orm.util.StandardSamples;

public class UserPasswords
        extends StandardSamples {

    public final UserPassword adminPasswd = new UserPassword();
    public final UserPassword guestPasswd = new UserPassword();

    @Inject
    Users users;

    @Override
    protected void preamble() {
        adminPasswd.setUser(users.admin);
        adminPasswd.setPasswd(UserPassword.digest("Bee32"));

        guestPasswd.setUser(users.guest);
        guestPasswd.setPasswd(UserPassword.digest("guest"));
    }

}
