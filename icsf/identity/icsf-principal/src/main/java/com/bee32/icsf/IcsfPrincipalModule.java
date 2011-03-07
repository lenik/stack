package com.bee32.icsf;

import com.bee32.icsf.principal.AbstractGroup;
import com.bee32.icsf.principal.AbstractRole;
import com.bee32.icsf.principal.AbstractUser;
import com.bee32.plover.orm.util.ERModule;
import com.bee32.plover.pub.oid.Oid;

// XXX Fix the oid.
@Oid({ 3, 13, 1 })
public class IcsfPrincipalModule
        extends ERModule {

    @Override
    protected void preamble() {
        exportEntity(AbstractUser.class, Long.class);
        exportEntity(AbstractGroup.class, Long.class);
        exportEntity(AbstractRole.class, Long.class);
    }

}
