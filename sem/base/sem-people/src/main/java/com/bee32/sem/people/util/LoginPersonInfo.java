package com.bee32.sem.people.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.bee32.icsf.login.LoginException;
import com.bee32.icsf.login.LoginInfo;
import com.bee32.plover.ox1.principal.AbstractPrincipalDto;
import com.bee32.plover.ox1.principal.Principal;
import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.sem.people.dto.OrgUnitDto;
import com.bee32.sem.people.dto.PersonDto;
import com.bee32.sem.people.dto.PersonRoleDto;

public class LoginPersonInfo
        implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SESSION_KEY = "loginPerson";

    static Logger logger = Logger.getLogger(LoginPersonInfo.class);

    PersonDto person;

    public LoginPersonInfo() {
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
            loginPersonInfo = new LoginPersonInfo();
            session.setAttribute(SESSION_KEY, loginPersonInfo);
        }

        return loginPersonInfo;
    }

    public PersonDto getPersonOpt() {
        return person;
    }

    public final PersonDto getPerson()
            throws LoginException {
        PersonDto person = getPersonOpt();
        if (person == null)
            throw new LoginException("Not login yet.");
        return person;
    }

    public void setPerson(PersonDto person) {
        this.person = person;
    }

    public void destroy() {
        HttpSession session = ThreadHttpContext.getSession();
        session.removeAttribute(SESSION_KEY);
    }

    public List<String> getChain() {
        List<String> chain = new ArrayList<String>();
        if (person == null) {
            for (AbstractPrincipalDto<? extends Principal> pnode : LoginInfo.getInstance().getChain())
                chain.add(pnode.getDisplayName());
        } else {
            Set<PersonRoleDto> roles = person.getRoles();
            if (!roles.isEmpty()) {
                PersonRoleDto firstRole = roles.iterator().next();
                OrgUnitDto orgUnit = firstRole.getOrgUnit();
                for (OrgUnitDto node : orgUnit.getChain()) {
                    String name = node.getName();
                    chain.add(name);
                }
            }

            // True Name (login)
            String loginName = LoginInfo.getInstance().getUser().getName();
            chain.add(person.getDisplayName() + " (" + loginName + ")");
        }
        return chain;
    }

}