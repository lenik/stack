package com.bee32.icsf.login;

import static com.bee32.icsf.login.UserPassword.digest;

import com.bee32.icsf.principal.Users;
import com.bee32.plover.orm.util.StandardSamples;

public class UserPasswords
        extends StandardSamples {

    public UserPassword admin = new UserPassword();
    public UserPassword guest = new UserPassword();

    Users users = predefined(Users.class);
    PrivateQuestions privateQuestions = predefined(PrivateQuestions.class);

    @Override
    protected void wireUp() {
        admin.init(users.admin, digest("Bee32"));
        admin.setResetQ(privateQuestions.DADS_NAME);
        guest.init(users.guest, digest("guest"));
        guest.setResetQ(privateQuestions.MOMS_NAME);
    }

}
