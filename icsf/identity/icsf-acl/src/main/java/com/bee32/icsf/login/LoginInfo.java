package com.bee32.icsf.login;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.icsf.principal.IGroupPrincipal;
import com.bee32.icsf.principal.IUserPrincipal;
import com.bee32.plover.servlet.util.ThreadHttpContext;

@Component
@Scope("session")
public class LoginInfo {

    static Logger logger = Logger.getLogger(LoginInfo.class);

    static final String CURRENT_CORP = "__current_corp";
    static final String CURRENT_DEPT = "__current_dept";
    static final String CURRENT_USER = "__current_user";

    final HttpSession session;
    static Map<String, Object> staticAttr = new HashMap<String, Object>();

    public LoginInfo() {
        this(ThreadHttpContext.getSession());
    }

    public LoginInfo(HttpSession session) {
        this.session = session;
    }

    Object get(String key) {
        if (session != null)
            return session.getAttribute(key);
        else
            return staticAttr.get(key);
    }

    void set(String key, Object value) {
        if (session != null)
            session.setAttribute(key, value);
        else
            staticAttr.put(key, value);
    }

    void remove(String key) {
        if (session != null)
            session.removeAttribute(key);
        else
            staticAttr.remove(key);
    }

    /**
     * 获取当前公司。
     *
     * @return 如果未定义（未登录）返回 <code>null</code>。
     */
    public IGroupPrincipal getCurrentCorp() {
        Object current_corp = get(CURRENT_CORP);
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
        Object current_dept = get(CURRENT_DEPT);

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
        Object current_user = get(CURRENT_USER);
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
        if (corp == null)
            remove(CURRENT_CORP);
        else
            set(CURRENT_CORP, corp);
    }

    /**
     * 设置当前部门。
     *
     * @param corp
     *            当前部门，用 <code>null</code> 用于清除当前设置。
     */
    public void setCurrentDepartment(IGroupPrincipal department) {
        if (department == null)
            remove(CURRENT_DEPT);
        else
            set(CURRENT_DEPT, department);
    }

    /**
     * 设置当前用户。
     *
     * @param corp
     *            当前用户，用 <code>null</code> 用于清除当前设置。
     */
    public void setCurrentUser(IUserPrincipal user) {
        if (user == null)
            remove(CURRENT_USER);
        else
            set(CURRENT_USER, user);
    }

}