package com.bee32.zebra.tk.site;

import net.bodz.lily.model.base.security.LoginData;

public class SiteVariables {

    public LoginData getLogin() {
        LoginData loginContext = LoginData.fromSession();
        return loginContext;
    }

}
