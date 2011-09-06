package com.bee32.sem.people.util;

import java.io.Serializable;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.icsf.login.LoginException;
import com.bee32.sem.people.dto.PersonDto;

@Component("loginPersonInfo")
@Scope("session")
public class LoginPersonInfoBean
        implements Serializable {

    private static final long serialVersionUID = 1L;

    public PersonDto getPersonOpt() {
        return LoginPersonInfo.getInstance().getPersonOpt();
    }

    public PersonDto getPerson()
            throws LoginException {
        return LoginPersonInfo.getInstance().getPerson();
    }

    public List<String> getChain() {
        return LoginPersonInfo.getInstance().getChain();
    }

}
