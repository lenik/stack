package com.bee32.sem.hr.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.bee32.sem.people.entity.Person;

@Entity
@DiscriminatorValue("EMP")
public class Employee
        extends Person {

    private static final long serialVersionUID = 1L;

}
