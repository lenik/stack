package com.bee32.sem.people.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.entity.EntityAuto;
import com.bee32.plover.orm.ext.color.Yellow;

@Entity
@Yellow
public class PersonLogin
        extends EntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    Person person;
    User user;

    @ManyToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

}
