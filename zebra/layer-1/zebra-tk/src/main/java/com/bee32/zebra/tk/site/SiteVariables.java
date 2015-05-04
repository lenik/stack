package com.bee32.zebra.tk.site;

import net.bodz.lily.model.base.security.LoginContext;

public class SiteVariables {

    public LoginContext getLogin() {
        LoginContext loginContext = LoginContext.fromSession();
        return loginContext;
    }

}
