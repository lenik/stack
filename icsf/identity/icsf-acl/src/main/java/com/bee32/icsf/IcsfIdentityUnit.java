package com.bee32.icsf;

import com.bee32.icsf.access.alt.R_ACE;
import com.bee32.icsf.access.dacl.DACE;
import com.bee32.icsf.access.dacl.DACL;
import com.bee32.icsf.login.PrivateQuestion;
import com.bee32.icsf.login.UserPassword;
import com.bee32.icsf.principal.Group;
import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.Role;
import com.bee32.icsf.principal.User;
import com.bee32.icsf.principal.UserEmail;
import com.bee32.plover.orm.PloverORMUnit;
import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;

@ImportUnit(PloverORMUnit.class)
public class IcsfIdentityUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        // add(Realm.class);

        add(Principal.class);
        add(User.class);
        add(Group.class);
        add(Role.class);
        add(UserEmail.class);

        add(UserPassword.class);
        add(PrivateQuestion.class);

        // add(R_ACL.class);
        add(R_ACE.class);

        add(DACL.class);
        add(DACE.class);
    }

}
