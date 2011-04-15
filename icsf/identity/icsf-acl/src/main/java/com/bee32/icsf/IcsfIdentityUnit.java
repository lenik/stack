package com.bee32.icsf;

import com.bee32.icsf.access.alt.R_ACE;
import com.bee32.icsf.principal.Group;
import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.Role;
import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.unit.PersistenceUnit;

public class IcsfIdentityUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {

        add(Principal.class);
        add(User.class);
        add(Group.class);
        add(Role.class);
        // add(Realm.class);

        // add(R_ACL.class);
        add(R_ACE.class);
    }

}
