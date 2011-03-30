package com.bee32.icsf.access.fea1;

import com.bee32.icsf.access.alt.R_ACE;
import com.bee32.icsf.access.alt.R_ACL;
import com.bee32.icsf.principal.Group;
import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.Role;
import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.config.test.TestPurposeSessionFactoryBean;

public class FeatureSFB
        extends TestPurposeSessionFactoryBean {

    {
        addPersistedClass(Principal.class);
        addPersistedClass(User.class);
        addPersistedClass(Group.class);
        addPersistedClass(Role.class);
        // addPersistedClass(Realm.class);

        addPersistedClass(R_ACL.class);
        addPersistedClass(R_ACE.class);
    }

}
