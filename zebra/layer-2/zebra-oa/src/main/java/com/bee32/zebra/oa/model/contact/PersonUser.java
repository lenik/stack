package com.bee32.zebra.oa.model.contact;

import java.io.Serializable;

import com.tinylily.model.base.security.User;

public class PersonUser
        implements Serializable {

    private static final long serialVersionUID = 1L;

    private Person person;
    private User user;

    public PersonUser(Person person, User user) {
        this.person = person;
        this.user = user;
    }

}
