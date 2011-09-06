package com.bee32.icsf.login;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.bee32.plover.ox1.principal.AbstractPrincipalDto;
import com.bee32.plover.ox1.principal.GroupDto;
import com.bee32.plover.ox1.principal.Principal;
import com.bee32.plover.ox1.principal.User;
import com.bee32.plover.ox1.principal.UserDto;
import com.bee32.plover.servlet.util.ThreadHttpContext;

public class LoginInfo
        implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SESSION_KEY = "login";

    static Logger logger = Logger.getLogger(LoginInfo.class);

    User internalUser;
    UserDto user;

    public LoginInfo() {
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
            loginInfo = new LoginInfo();
            session.setAttribute(SESSION_KEY, loginInfo);
        }

        return loginInfo;
    }

    public User getInternalUserOpt() {
        return internalUser;
    }

    public final User getInternalUser()
            throws LoginException {
        User user = getInternalUserOpt();
        if (user == null)
            throw new LoginException("Not login yet.");
        return user;
    }

    public void setInternalUser(User user) {
        this.internalUser = user;
    }

    public UserDto getUserOpt() {
        return user;
    }

    public final UserDto getUser()
            throws LoginException {
        UserDto user = getUserOpt();
        if (user == null)
            throw new LoginException("Not login yet.");
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public synchronized void runAs(User user, Runnable runnable) {
        User savedInternalUser = this.internalUser;
        // UserDto savedUser = this.user;
        this.internalUser = user;
        try {
            runnable.run();
        } finally {
            this.internalUser = savedInternalUser;
        }
    }

    public void destroy() {
        HttpSession session = ThreadHttpContext.getSession();
        session.removeAttribute(SESSION_KEY);
    }

    public List<AbstractPrincipalDto<? extends Principal>> getChain() {
        List<AbstractPrincipalDto<? extends Principal>> chain = new ArrayList<AbstractPrincipalDto<? extends Principal>>();
        if (user == null) {
            UserDto dummy = new UserDto();
            dummy.setName("(n/a)");
            chain.add(dummy);
            return chain;
        }

        chain.add(user);

        GroupDto group = user.getFirstGroup();
        while (group != null) {
            chain.add(group);
            group = group.getInheritedGroup();
        }

        Collections.reverse(chain);
        return chain;
    }

}