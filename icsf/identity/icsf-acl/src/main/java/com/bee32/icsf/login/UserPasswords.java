package com.bee32.icsf.login;

import com.bee32.icsf.principal.Users;
import com.bee32.plover.orm.util.StandardSamples;

public class UserPasswords
        extends StandardSamples {

    public final UserPassword adminPasswd = new UserPassword();
    public final UserPassword guestPasswd = new UserPassword();

    Users users = predefined(Users.class);

    @Override
    protected void wireUp() {
        adminPasswd.setUser(users.admin);
        adminPasswd.setPasswd(UserPassword.digest("Bee32"));

        guestPasswd.setUser(users.guest);
        guestPasswd.setPasswd(UserPassword.digest("guest"));
    }

}
