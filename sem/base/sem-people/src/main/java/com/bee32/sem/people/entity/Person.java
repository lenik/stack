package com.bee32.sem.people.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.ext.xp.EntityExt;

/**
 * 自然人或法人。
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "stereo", length = 4)
@DiscriminatorValue("-")
public class Person
        extends EntityExt<Long, PersonXP> {

    private static final long serialVersionUID = 1L;

    User user;

    String name;
    String fullName;
    String nickName;

    Gender sex;

    Date birthday;

    String interests;

    String censusRegister;
    String sidType;
    String sid;

    List<Contact> contacts;
    List<PersonLog> logs;

    @OneToMany
    @Override
    protected List<PersonXP> getXPool() {
        return pool();
    }

    @Override
    protected void setXPool(List<PersonXP> xPool) {
        pool(xPool);
    }

    @Basic(optional = false)
    @Column(length = 30, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
