package com.bee32.sem.people.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.icsf.login.LoginException;
import com.bee32.icsf.login.LoginInfo;
import com.bee32.plover.ox1.principal.Principal;
import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.sem.people.entity.OrgUnit;
import com.bee32.sem.people.entity.Person;
import com.bee32.sem.people.entity.PersonRole;

@Component
@Scope("session")
public class LoginPersonInfo
        implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SESSION_KEY = "loginPerson";

    static Logger logger = Logger.getLogger(LoginPersonInfo.class);

    final HttpSession session;
    Person person;

    public LoginPersonInfo() {
        LoginPersonInfo current = getInstance();
        this.session = current.session;
        this.person = current.person;
    }

    public LoginPersonInfo(HttpSession session) {
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

    public final Person getPerson() {
        Person person = getPersonOpt();
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

    public List<String> getChain() {
        List<String> chain = new ArrayList<String>();
        if (person == null) {
            for (Principal pnode : LoginInfo.getInstance().getChain())
                chain.add(pnode.getDisplayName());
        } else {
            Set<PersonRole> roles = person.getRoles();
            if (!roles.isEmpty()) {
                PersonRole firstRole = roles.iterator().next();
                OrgUnit orgUnit = firstRole.getOrgUnit();
                for (OrgUnit node : orgUnit.getChain()) {
                    String name = node.getName();
                    chain.add(name);
                }
            }
            chain.add(person.getDisplayName());
        }
        return chain;
    }

}