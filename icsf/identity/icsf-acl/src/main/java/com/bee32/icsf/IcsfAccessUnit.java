package com.bee32.icsf;

import com.bee32.icsf.access.acl.ACL;
import com.bee32.icsf.access.acl.ACLEntry;
import com.bee32.icsf.access.acl.ACLPref;
import com.bee32.icsf.access.alt.R_ACE;
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

        add(ACL.class);
        add(ACLEntry.class);
        // add(R_ACL.class);
        add(R_ACE.class);
        add(ACLPref.class);
    }

}
