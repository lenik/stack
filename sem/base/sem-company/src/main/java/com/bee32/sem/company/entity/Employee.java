package com.bee32.sem.company.entity;

import javax.free.Person;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.icsf.principal.Role;
import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.entity.IParentAware;

@Entity
// @Table("Person")
@DiscriminatorValue("EMP")
public class Employee
        extends User
        implements IParentAware<Department> {

    private static final long serialVersionUID = 1L;

    String empId;

    Person person = new Person();

    public Employee() {
        super();
    }

    public Employee(String name) {
        super(name);
    }

    public Employee(String name, Department department, Role primaryRole) {
        super(name, department, primaryRole);
    }

    @OneToOne(optional = false)
    @Cascade({ CascadeType.ALL })
    public Party getPerson() {
        return person;
    }

    public void setPerson(Party person) {
        if (person == null)
            throw new NullPointerException("person");
        this.person = person;
    }

    @Override
    public void setName(String name) {
        super.setName(name);
        person.setName(name);
    }

    @Transient
    @Override
    public Department getParent() {
        return getDepartment();
    }

    @Override
    public void setParent(Department parent) {
        setDepartment(parent);
    }

    @Transient
    public Department getDepartment() {
        return (Department) getPrimaryGroup();
    }

    public void setDepartment(Department department) {
        setPrimaryGroup(department);
    }

}
