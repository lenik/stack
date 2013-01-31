package com.bee32.ape.engine.base;

public interface IApeActivitiAdapter
        extends IAppCtxAccess {

    Class<com.bee32.icsf.principal.Principal> icsfPrincipalType = com.bee32.icsf.principal.Principal.class;
    Class<com.bee32.icsf.principal.User> icsfUserType = com.bee32.icsf.principal.User.class;
    Class<com.bee32.icsf.principal.Role> icsfGroupType = com.bee32.icsf.principal.Role.class;

    String GROUP_EXT = "role";

}
