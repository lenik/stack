package com.bee32.icsf.login;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.plover.ox1.principal.AbstractPrincipalDto;
import com.bee32.plover.ox1.principal.GroupDto;
import com.bee32.plover.ox1.principal.Principal;
import com.bee32.plover.ox1.principal.User;
import com.bee32.plover.ox1.principal.UserDto;
import com.bee32.plover.servlet.util.ThreadHttpContext;

@Component
@Scope("session")
public class LoginInfo
        implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SESSION_KEY = "login";

    static Logger logger = Logger.getLogger(LoginInfo.class);

    final HttpSession session;
    User internalUser;
    UserDto user;

    /**
     * For {@link NullLoginInfo} only.
     */
    public LoginInfo() {
        // Copy from instance for current session.
        LoginInfo current = getInstance();
        this.session = current.session;
        this.internalUser = current.internalUser;
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

    public User getInternalUserOpt() {
        return internalUser;
    }

    public final User getInternalUser() {
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

    public final UserDto getUser() {
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
        session.removeAttribute(SESSION_KEY);
    }

    public List<AbstractPrincipalDto<? extends Principal>> getChain() {
        List<AbstractPrincipalDto<? extends Principal>> chain = new ArrayList<AbstractPrincipalDto<? extends Principal>>();
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