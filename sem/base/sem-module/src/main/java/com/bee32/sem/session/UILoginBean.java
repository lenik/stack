package com.bee32.sem.session;

import com.bee32.icsf.login.SessionLoginInfo;
import com.bee32.plover.web.faces.view.ViewBean;

public class UILoginBean
        extends ViewBean {

    private static final long serialVersionUID = 1L;

    public String getUserName() {
        String name = SessionLoginInfo.getUser().getName();
        return name;
    }

}
