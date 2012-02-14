package com.bee32.icsf.login;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.bee32.icsf.principal.GroupDto;
import com.bee32.icsf.principal.PrincipalDto;
import com.bee32.icsf.principal.User;
import com.bee32.icsf.principal.UserDto;
import com.bee32.plover.orm.util.DefaultDataAssembledContext;
import com.bee32.plover.servlet.util.ThreadHttpContext;

public class SessionUser
        implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SESSION_KEY = "login";

    static Logger logger = Logger.getLogger(SessionUser.class);

    UserDto user;

    User internalUserOverride;
    Set<Integer> imIdSet = new HashSet<Integer>();
    Set<Integer> invIdSet = new HashSet<Integer>();

    List<PrincipalDto> imSet = new ArrayList<PrincipalDto>();
    List<PrincipalDto> invSet = new ArrayList<PrincipalDto>();

    public SessionUser() {
    }

    public static SessionUser getInstance() {
        HttpSession session = ThreadHttpContext.getSessionOpt();

        if (session == null)
            return NullSessionUser.INSTANCE;

        return getInstance(session);
    }

    public static synchronized SessionUser getInstance(HttpSession session) {
        if (session == null)
            throw new NullPointerException("session");

        SessionUser loginInfo = (SessionUser) session.getAttribute(SESSION_KEY);
        if (loginInfo == null) {
            loginInfo = new SessionUser();
            session.setAttribute(SESSION_KEY, loginInfo);
        }

        return loginInfo;
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

    protected static class ctx
            extends DefaultDataAssembledContext {
    }

    public User getInternalUserOpt() {
        if (internalUserOverride != null)
            return internalUserOverride;
        if (user == null || user.isNull())
            return null;
        Integer userId = user.getId();
        User internalUser = ctx.data.getRef(User.class, userId);
        return internalUser;
    }

    public final User getInternalUser()
            throws LoginException {
        User user = getInternalUserOpt();
        if (user == null)
            throw new LoginException("Not login yet.");
        return user;
    }

    public Set<Integer> getImIdSet() {
        return imIdSet;
    }

    public void setImIdSet(Set<Integer> imIdSet) {
        if (imIdSet == null)
            throw new NullPointerException("imIdSet");
        this.imIdSet = imIdSet;
    }

    public Set<Integer> getInvIdSet() {
        return invIdSet;
    }

    public void setInvIdSet(Set<Integer> invIdSet) {
        if (invIdSet == null)
            throw new NullPointerException("invIdSet");
        this.invIdSet = invIdSet;
    }

    public List<PrincipalDto> getImSet() {
        return imSet;
    }

    public void setImSet(List<PrincipalDto> imSet) {
        if (imSet == null)
            throw new NullPointerException("imSet");
        this.imSet = imSet;
    }

    public List<PrincipalDto> getInvSet() {
        return invSet;
    }

    public void setInvSet(List<PrincipalDto> invSet) {
        if (invSet == null)
            throw new NullPointerException("invSet");
        this.invSet = invSet;
    }

    public synchronized void runAs(User user, Runnable runnable) {
        User savedInternalUser = this.internalUserOverride;
        // UserDto savedUser = this.user;
        this.internalUserOverride = user;
        try {
            runnable.run();
        } finally {
            this.internalUserOverride = savedInternalUser;
        }
    }

    public void destroy() {
        HttpSession session = ThreadHttpContext.getSession();
        session.removeAttribute(SESSION_KEY);
    }

    public List<PrincipalDto> getChain() {
        List<PrincipalDto> chain = new ArrayList<PrincipalDto>();
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