package com.bee32.icsf.principal;

import com.bee32.plover.orm.unit.PersistenceUnit;

public class IcsfPrincipalUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(Principal.class);
        add(User.class);
        add(Group.class);
        add(Role.class);
        // add(Realm.class);
    }

}
