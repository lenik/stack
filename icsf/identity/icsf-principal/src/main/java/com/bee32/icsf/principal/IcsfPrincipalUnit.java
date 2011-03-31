package com.bee32.icsf.principal;

import com.bee32.plover.orm.unit.PUnit;

public class IcsfPrincipalUnit
        extends PUnit {

    @Override
    protected void preamble() {
        add(Principal.class);
        add(User.class);
        add(Group.class);
        add(Role.class);
        // addPersistedClass(Realm.class);
    }

}
