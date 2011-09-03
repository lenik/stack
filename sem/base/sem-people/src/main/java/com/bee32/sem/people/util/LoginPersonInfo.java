package com.bee32.sem.people.util;

import java.io.Serializable;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.bee32.icsf.login.LoginException;
import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.sem.people.entity.Person;

public class LoginPersonInfo
        implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SESSION_KEY = "loginPerson";

    static Logger logger = Logger.getLogger(LoginPersonInfo.class);

    final HttpSession session;
    Person person;

    LoginPersonInfo() {
        this.session = null;
    }

    public LoginPersonInfo(HttpSession session) {
        if (session == null)
            throw new NullPointerException("session");
        this.session = session;
    }

    public static LoginPersonInfo getInstance() {
        HttpSession session = ThreadHttpContext.getSessionOpt();

        if (session == null)
            return NullLoginPersonInfo.INSTANCE;

        return getInstance(session);
    }

    public static synchronized LoginPersonInfo getInstance(HttpSession session) {
        if (session == null)
            throw new NullPointerException("session");

        LoginPersonInfo loginPersonInfo = (LoginPersonInfo) session.getAttribute(SESSION_KEY);
        if (loginPersonInfo == null) {
            loginPersonInfo = new LoginPersonInfo(session);
            session.setAttribute(SESSION_KEY, loginPersonInfo);
        }

        return loginPersonInfo;
    }

    public Person getPersonOpt() {
        return person;
    }

    public Person getPerson() {
        if (person == null)
            throw new LoginException("Not login yet.");
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void destroy() {
        session.removeAttribute(SESSION_KEY);
    }

}