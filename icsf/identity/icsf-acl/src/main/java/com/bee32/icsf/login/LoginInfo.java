package com.bee32.icsf.login;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.plover.ox1.principal.Group;
import com.bee32.plover.ox1.principal.Principal;
import com.bee32.plover.ox1.principal.User;
import com.bee32.plover.servlet.util.ThreadHttpContext;

@Component
@Scope("session")
public class LoginInfo
        implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SESSION_KEY = "login";

    static Logger logger = Logger.getLogger(LoginInfo.class);

    final HttpSession session;
    User user;

    /**
     * For {@link NullLoginInfo} only.
     */
    public LoginInfo() {
        // Copy from instance for current session.
        LoginInfo current = getInstance();
        this.session = current.session;
        this.user = current.user;
    }

    public LoginInfo(HttpSession session) {
        this.session = session;
    }

    public static LoginInfo getInstance() {
        HttpSession session = ThreadHttpContext.getSessionOpt();

        if (session == null)
            return NullLoginInfo.INSTANCE;

        return getInstance(session);
    }

    public static synchronized LoginInfo getInstance(HttpSession session) {
        if (session == null)
            throw new NullPointerException("session");

        LoginInfo loginInfo = (LoginInfo) session.getAttribute(SESSION_KEY);
        if (loginInfo == null) {
            loginInfo = new LoginInfo(session);
            session.setAttribute(SESSION_KEY, loginInfo);
        }

        return loginInfo;
    }

    public User getUserOpt() {
        return user;
    }

    public final User getUser() {
        User user = getUserOpt();
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

    public List<Principal> getChain() {
        List<Principal> chain = new ArrayList<Principal>();
        chain.add(user);

        Group group = user.getFirstGroup();
        while (group != null) {
            chain.add(group);
            group = group.getInheritedGroup();
        }

        Collections.reverse(chain);
        return chain;
    }

}