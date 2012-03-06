package com.bee32.sem.people.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.NaturalId;

import com.bee32.icsf.access.DefaultPermission;
import com.bee32.icsf.access.Permission;
import com.bee32.icsf.principal.User;
import com.bee32.plover.arch.util.DummyId;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.ox1.c.CEntityAuto;
import com.bee32.plover.ox1.color.Yellow;

@Entity
@Yellow
@DefaultPermission(Permission.R_X)
@SequenceGenerator(name = "idgen", sequenceName = "person_login_seq", allocationSize = 1)
public class PersonLogin
        extends CEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    Person person;
    User user;

    @Override
    public void populate(Object source) {
        if (source instanceof PersonLogin)
            _populate((PersonLogin) source);
        else
            super.populate(source);
    }

    protected void _populate(PersonLogin o) {
        super._populate(o);
        person = o.person;
        user = o.user;
    }

    @NaturalId
    @OneToOne(optional = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(optional = false)
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    protected Serializable naturalId() {
        if (user == null)
            return new DummyId(this);
        return user;
    }

    @Override
    protected ICriteriaElement selector(String prefix) {
        if (user == null)
            throw new NullPointerException("user");
        return selector(prefix + "user", user);
    }

}
