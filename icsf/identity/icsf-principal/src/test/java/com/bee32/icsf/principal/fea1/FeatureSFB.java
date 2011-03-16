package com.bee32.icsf.principal.fea1;

import com.bee32.icsf.principal.Group;
import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.Role;
import com.bee32.icsf.principal.User;
import com.bee32.icsf.principal.realm.Realm;
import com.bee32.plover.orm.config.TestPurposeSessionFactoryBean;

public class FeatureSFB
        extends TestPurposeSessionFactoryBean {

    {
        addPersistedClass(Principal.class);
        addPersistedClass(User.class);
        addPersistedClass(Group.class);
        addPersistedClass(Role.class);
        addPersistedClass(Realm.class);
    }

}
