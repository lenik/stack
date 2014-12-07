package com.bee32.zebra.oa.contact;

import java.io.Serializable;

import com.tinylily.model.base.security.User;

/**
 * person -> user*
 */
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
