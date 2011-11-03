package com.bee32.sem.people.util;

import com.bee32.icsf.login.LoginAdapter;
import com.bee32.icsf.login.LoginEvent;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.ox1.principal.User;
import com.bee32.sem.people.dto.PersonDto;
import com.bee32.sem.people.entity.Person;
import com.bee32.sem.people.entity.PersonLogin;

/**
 * 将登录用户对应的 Person 转换为 DTO 并记录到 session 中。
 */
public class DetailedLoginAdapter
        extends LoginAdapter {

    @Override
    public void logIn(LoginEvent event) {
        User user = event.getUser();

        PersonLogin personLogin = null;
        if (user != null)
            personLogin = asFor(PersonLogin.class).getFirst(new Equals("user.id", user.getId()));

        if (personLogin != null) {
            Person _person = personLogin.getPerson();
            PersonDto person = DTOs.mref(PersonDto.class, //
                    PersonDto.ROLES_CHAIN, // PersonRole -> OrgUnit*
                    _person);
            LoginPersonInfo.getInstance().setPerson(person);
        }
    }

    @Override
    public void logOut(LoginEvent event) {
        LoginPersonInfo.getInstance().destroy();
    }

}
