package com.bee32.icsf.login;

import javax.servlet.http.HttpSession;

import com.bee32.plover.ox1.principal.User;

public abstract class SessionLoginInfo {

    public static User getUserOpt(HttpSession session) {
        return LoginInfo.getInstance(session).getUserOpt();
    }

    public static User getUser(HttpSession session) {
        return LoginInfo.getInstance(session).getUser();
    }

}
