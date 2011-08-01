package com.bee32.sem.people.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.NaturalId;

import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.entity.EntityAuto;
import com.bee32.plover.orm.entity.EntityBase;
import com.bee32.plover.orm.ext.color.Yellow;

@Entity
@Yellow
@SequenceGenerator(name = "idgen", sequenceName = "person_login_seq", allocationSize = 1)
public class PersonLogin
        extends EntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    Person person;
    User user;

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
    protected Boolean naturalEquals(EntityBase<Long> other) {
        PersonLogin o = (PersonLogin) other;

        if (!user.equals(o.user))
            return false;

        return true;
    }

    @Override
    protected Integer naturalHashCode() {
        int hash = 0;
        hash += user.hashCode();
        return hash;
    }

}
