package com.bee32.icsf;

import com.bee32.icsf.access.alt.R_ACE;
import com.bee32.icsf.access.dacl.DACE;
import com.bee32.icsf.access.dacl.DACL;
import com.bee32.icsf.login.PrivateQuestion;
import com.bee32.icsf.login.UserPassword;
import com.bee32.icsf.principal.IcsfPrincipalUnit;
import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;

@ImportUnit(IcsfPrincipalUnit.class)
public class IcsfAccessUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        // add(Realm.class);

        add(UserPassword.class);
        add(PrivateQuestion.class);

        // add(R_ACL.class);
        add(R_ACE.class);

        add(DACL.class);
        add(DACE.class);
    }

}
