package com.bee32.icsf.login;

import com.bee32.icsf.principal.Users;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.sample.StandardSamples;

public class UserPasswords
        extends StandardSamples {

    public UserPassword admin = new UserPassword();
    public UserPassword guest = new UserPassword();

    Users users = predefined(Users.class);
    PrivateQuestions privateQuestions = predefined(PrivateQuestions.class);

    @Override
    protected void decorate(Entity<?> sample) {
        super.decorate(sample);
        sample.getEntityFlags().setOverrided(true);
    }

    @Override
    protected void wireUp() {
        admin.init(users.admin, ("Bee32"));
        admin.setResetQ(privateQuestions.DADS_NAME);
        guest.init(users.guest, ("guest"));
        guest.setResetQ(privateQuestions.MOMS_NAME);
    }

}
