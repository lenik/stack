package com.bee32.sem.session;

import com.bee32.icsf.login.LoginControl;
import com.bee32.icsf.login.SessionUser;
import com.bee32.plover.faces.view.ViewBean;

public class UILoginBean
        extends ViewBean {

    private static final long serialVersionUID = 1L;

    public String getUserName()
            throws LoginControl {
        String name = SessionUser.getInstance().getUser().getName();
        return name;
    }

}
