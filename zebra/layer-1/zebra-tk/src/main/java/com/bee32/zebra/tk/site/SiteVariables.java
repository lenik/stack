package com.bee32.zebra.tk.site;

import com.tinylily.model.base.security.LoginContext;

public class SiteVariables {

    public LoginContext getLogin() {
        LoginContext loginContext = LoginContext.fromSession();
        return loginContext;
    }

}
