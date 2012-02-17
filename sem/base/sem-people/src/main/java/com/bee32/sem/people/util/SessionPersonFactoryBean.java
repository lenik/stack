package com.bee32.sem.people.util;

import java.util.List;

import org.springframework.stereotype.Component;

import com.bee32.icsf.login.LoginControl;
import com.bee32.plover.inject.scope.PerSession;
import com.bee32.sem.people.dto.PersonDto;

@Component("sessionPerson")
@PerSession
public class SessionPersonFactoryBean {

    public PersonDto getPersonOpt() {
        return SessionPerson.getInstance().getPersonOpt();
    }

    public final PersonDto getPerson()
            throws LoginControl {
        return SessionPerson.getInstance().getPerson();
    }

    public void setPerson(PersonDto person) {
        SessionPerson.getInstance().setPerson(person);
    }

    public List<String> getChain() {
        return SessionPerson.getInstance().getChain();
    }

}
