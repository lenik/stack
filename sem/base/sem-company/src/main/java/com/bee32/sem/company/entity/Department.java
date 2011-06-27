package com.bee32.sem.company.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.bee32.icsf.principal.Group;

@Entity
// @Table(name = "Department")
@DiscriminatorValue("DPT")
public class Department
        extends Group {

    private static final long serialVersionUID = 1L;

    public Department() {
        super();
    }

    public Department(String name) {
        super(name);
    }

    public Department(String name, Employee owner, Employee... employees) {
        super(name, owner, employees);
    }

}
