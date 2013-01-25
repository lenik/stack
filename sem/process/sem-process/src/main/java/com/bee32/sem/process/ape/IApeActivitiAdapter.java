package com.bee32.sem.process.ape;

import com.bee32.plover.orm.util.DefaultDataAssembledContext;

public interface IApeActivitiAdapter {

    class ctx
            extends DefaultDataAssembledContext {
    }

    Class<com.bee32.icsf.principal.User> semUserType = com.bee32.icsf.principal.User.class;
    Class<com.bee32.icsf.principal.Group> semGroupType = com.bee32.icsf.principal.Group.class;
    Class<com.bee32.icsf.principal.Role> semRoleType = com.bee32.icsf.principal.Role.class;

}
