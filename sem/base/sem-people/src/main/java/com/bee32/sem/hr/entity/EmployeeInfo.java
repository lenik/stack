package com.bee32.sem.hr.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Transient;

import com.bee32.plover.orm.entity.EntityAuto;
import com.bee32.sem.people.entity.Person;

@Entity
public class EmployeeInfo
        extends EntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    Person person;

    JobPost role;
    JobTitle title;
    JobPerformance jobPerformance;
    int duty;
    int workAbility;

    Date employedDate;
    Date resignedDate;

    List<LaborContract> laborContracts;

    @Transient
    public int getWorkYears() {
        // return resignedDate - employedDate
        return 0;
    }

}
