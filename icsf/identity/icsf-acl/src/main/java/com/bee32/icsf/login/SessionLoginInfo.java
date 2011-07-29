package com.bee32.icsf.login;

import javax.servlet.http.HttpSession;

import com.bee32.icsf.principal.IGroupPrincipal;
import com.bee32.icsf.principal.IUserPrincipal;
import com.bee32.plover.servlet.util.ThreadServletContext;

public abstract class SessionLoginInfo {

    public static IUserPrincipal getCurrentUser(HttpSession session) {
        if (session == null)
            return null;
        return new LoginInfo(session).getCurrentUser();
    }

    public static IUserPrincipal requireCurrentUser(HttpSession session) {
        IUserPrincipal currentUser = getCurrentUser(session);
        if (currentUser == null)
            throw new LoginException("Not login yet.");
        return currentUser;
    }

    public static IGroupPrincipal getCurrentCorp(HttpSession session) {
        if (session == null)
            return null;
        return new LoginInfo(session).getCurrentCorp();
    }

    public static IGroupPrincipal getCurrentDepartment(HttpSession session) {
        return new LoginInfo(session).getCurrentDepartment();
    }

    public static void setCurrentCorp(HttpSession session, IGroupPrincipal corp) {
        new LoginInfo(session).setCurrentCorp(corp);
    }

    public static void setCurrentDepartment(HttpSession session, IGroupPrincipal department) {
        new LoginInfo(session).setCurrentDepartment(department);
    }

    public static void setCurrentUser(HttpSession session, IUserPrincipal user) {
        new LoginInfo(session).setCurrentUser(user);
    }

    // Thread local session:

    /**
     * @return <code>null</code> If not logged in yet.
     */
    public static IUserPrincipal getCurrentUser() {
        return getCurrentUser(ThreadServletContext.getSession());
    }

    public static void setCurrentUser(IUserPrincipal user) {
        setCurrentUser(ThreadServletContext.requireSession(), user);
    }

    public static IUserPrincipal requireCurrentUser() {
        return requireCurrentUser(ThreadServletContext.requireSession());

    }

    public static IGroupPrincipal getCurrentCorp() {
        return getCurrentCorp(ThreadServletContext.getSession());
    }

    public static void setCurrentCorp(IGroupPrincipal corp) {
        setCurrentCorp(ThreadServletContext.requireSession(), corp);
    }

    public static IGroupPrincipal getCurrentDepartment() {
        return getCurrentDepartment(ThreadServletContext.getSession());
    }

}
