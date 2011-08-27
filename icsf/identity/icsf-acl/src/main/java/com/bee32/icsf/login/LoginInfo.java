package com.bee32.icsf.login;

import java.io.Serializable;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.bee32.plover.ox1.principal.User;
import com.bee32.plover.servlet.util.ThreadHttpContext;

public class LoginInfo
        implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SESSION_KEY = "login";

    static Logger logger = Logger.getLogger(LoginInfo.class);

    HttpSession session;
    User user;

    public static LoginInfo getInstance() {
        HttpSession session = ThreadHttpContext.getSession();

        if (session == null)
            return NullLoginInfo.INSTANCE;

        return getInstance(session);
    }

    public static LoginInfo getInstance(HttpSession session) {
        if (session == null)
            throw new NullPointerException("session");

        LoginInfo loginInfo = (LoginInfo) session.getAttribute(SESSION_KEY);
        if (loginInfo == null)
            return NullLoginInfo.INSTANCE;

        return loginInfo;
    }

    public User getUserOpt() {
        return user;
    }

    public User getUser() {
        if (user == null)
            throw new LoginException("Not login yet.");
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public synchronized void runAs(User user, Runnable runnable) {
        User _saved = this.user;
        this.user = user;
        try {
            runnable.run();
        } finally {
            this.user = _saved;
        }
    }

    public void destroy() {
        session.removeAttribute(SESSION_KEY);
    }

}