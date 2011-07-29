package com.bee32.icsf.login;

import javax.servlet.http.HttpSession;

import com.bee32.icsf.principal.IGroupPrincipal;
import com.bee32.icsf.principal.IUserPrincipal;
import com.bee32.plover.servlet.util.ThreadServletContext;

public abstract class SessionLoginInfo {

    public static IUserPrincipal getUserOpt(HttpSession session) {
        if (session == null)
            return null;
        return new LoginInfo(session).getCurrentUser();
    }

    public static IUserPrincipal getUser(HttpSession session) {
        IUserPrincipal currentUser = getUserOpt(session);
        if (currentUser == null)
            throw new LoginException("Not login yet.");
        return currentUser;
    }

    public static IGroupPrincipal getCorp(HttpSession session) {
        if (session == null)
            return null;
        return new LoginInfo(session).getCurrentCorp();
    }

    public static IGroupPrincipal getDepartment(HttpSession session) {
        return new LoginInfo(session).getCurrentDepartment();
    }

    public static void setCorp(HttpSession session, IGroupPrincipal corp) {
        new LoginInfo(session).setCurrentCorp(corp);
    }

    public static void setDepartment(HttpSession session, IGroupPrincipal department) {
        new LoginInfo(session).setCurrentDepartment(department);
    }

    public static void setUser(HttpSession session, IUserPrincipal user) {
        new LoginInfo(session).setCurrentUser(user);
    }

    // Thread local session:

    /**
     * @return <code>null</code> If not logged in yet.
     */
    public static IUserPrincipal getUserOpt() {
        return getUserOpt(ThreadServletContext.getSessionOpt());
    }

    public static IUserPrincipal getUser() {
        return getUser(ThreadServletContext.getSession());
    }

    public static void setUser(IUserPrincipal user) {
        setUser(ThreadServletContext.getSession(), user);
    }

    public static IGroupPrincipal getCorpOpt() {
        return getCorp(ThreadServletContext.getSessionOpt());
    }

    public static void setCorp(IGroupPrincipal corp) {
        setCorp(ThreadServletContext.getSession(), corp);
    }

    public static IGroupPrincipal getDepartmentOpt() {
        return getDepartment(ThreadServletContext.getSessionOpt());
    }

}
