package com.bee32.sem.user.util;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.bee32.icsf.principal.IGroupPrincipal;
import com.bee32.icsf.principal.IUserPrincipal;
import com.bee32.plover.servlet.util.ThreadServletContext;

public class SessionLoginInfo {

    static Logger logger = Logger.getLogger(SessionLoginInfo.class);

    static final String CURRENT_CORP = "__current_corp";
    static final String CURRENT_DEPT = "__current_dept";
    static final String CURRENT_USER = "__current_user";

    HttpSession session;

    public SessionLoginInfo() {
    }

    public SessionLoginInfo(HttpSession session) {
        this.session = session;
    }

    protected HttpSession getSession() {
        if (session != null)
            return session;
        return ThreadServletContext.getSession();
    }

    protected HttpSession requireSession() {
        if (session != null)
            return session;
        return ThreadServletContext.requireSession();
    }

    protected void setSession(HttpSession session) {
        this.session = session;
        // Not used:
        // ThreadServletContext.setSession(session);
    }

    /**
     * 获取当前公司。
     *
     * @return 如果未定义（未登录）返回 <code>null</code>。
     */
    public IGroupPrincipal getCurrentCorp() {
        if (getSession() == null)
            return null;

        Object current_corp = getSession().getAttribute(CURRENT_CORP);
        if (current_corp == null)
            return null;

        if (current_corp instanceof IGroupPrincipal)
            return (IGroupPrincipal) current_corp;

        logger.error("Invalid current_corp in session: " + current_corp.getClass());
        return null;
    }

    /**
     * 获取当前部门。
     *
     * @return 如果未定义（未登录）返回 <code>null</code>。
     */
    public IGroupPrincipal getCurrentDepartment() {
        if (getSession() == null)
            return null;

        Object current_dept = getSession().getAttribute(CURRENT_DEPT);

        if (current_dept == null) {
            IUserPrincipal currentUser = getCurrentUser();
            if (currentUser != null)
                return currentUser.getPrimaryGroup();
            else
                return null;
        }

        if (current_dept instanceof IGroupPrincipal)
            return (IGroupPrincipal) current_dept;

        logger.error("Invalid current_dept in session: " + current_dept.getClass());
        return null;
    }

    /**
     * 获取当前用户。
     *
     * @return 如果未定义（未登录）返回 <code>null</code>。
     */
    public IUserPrincipal getCurrentUser() {
        if (getSession() == null)
            return null;

        Object current_user = getSession().getAttribute(CURRENT_USER);
        if (current_user == null)
            return null;

        if (current_user instanceof IUserPrincipal)
            return (IUserPrincipal) current_user;

        logger.error("Invalid current_user in session: " + current_user.getClass());
        return null;
    }

    /**
     * 设置当前公司。
     *
     * @param corp
     *            当前公司，用 <code>null</code> 用于清除当前设置。
     */
    public void setCurrentCorp(IGroupPrincipal corp) {
        HttpSession session = requireSession();

        if (corp == null)
            session.removeAttribute(CURRENT_CORP);
        else
            session.setAttribute(CURRENT_CORP, corp);
    }

    /**
     * 设置当前部门。
     *
     * @param corp
     *            当前部门，用 <code>null</code> 用于清除当前设置。
     */
    public void setCurrentDepartment(IGroupPrincipal department) {
        HttpSession session = requireSession();

        if (department == null)
            session.removeAttribute(CURRENT_DEPT);
        else
            session.setAttribute(CURRENT_DEPT, department);
    }

    /**
     * 设置当前用户。
     *
     * @param corp
     *            当前用户，用 <code>null</code> 用于清除当前设置。
     */
    public void setCurrentUser(IUserPrincipal user) {
        HttpSession session = requireSession();

        if (user == null)
            session.removeAttribute(CURRENT_USER);
        else
            session.setAttribute(CURRENT_USER, user);
    }

    // Statics.
    public static IUserPrincipal getCurrentUser(HttpSession session) {
        return new SessionLoginInfo(session).getCurrentUser();
    }

    public static IUserPrincipal requireCurrentUser(HttpSession session) {
        IUserPrincipal currentUser = getCurrentUser(session);
        if (currentUser == null)
            throw new IllegalStateException("Not login yet.");
        return currentUser;
    }

    public static IGroupPrincipal getCurrentCorp(HttpSession session) {
        return new SessionLoginInfo(session).getCurrentCorp();
    }

    public static IGroupPrincipal getCurrentDepartment(HttpSession session) {
        return new SessionLoginInfo(session).getCurrentDepartment();
    }

    public static void setCurrentCorp(HttpSession session, IGroupPrincipal corp) {
        new SessionLoginInfo(session).setCurrentCorp(corp);
    }

    public static void setCurrentDepartment(HttpSession session, IGroupPrincipal department) {
        new SessionLoginInfo(session).setCurrentDepartment(department);
    }

    public static void setCurrentUser(HttpSession session, IUserPrincipal user) {
        new SessionLoginInfo(session).setCurrentUser(user);
    }

}