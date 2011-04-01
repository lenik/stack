package com.bee32.icsf.access;

import com.bee32.icsf.access.alt.R_ACE;
import com.bee32.icsf.access.alt.R_ACL;
import com.bee32.icsf.principal.IcsfPrincipalUnit;
import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;

@ImportUnit(IcsfPrincipalUnit.class)
public class IcsfAccessUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(R_ACL.class);
        add(R_ACE.class);
    }

}
