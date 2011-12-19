package com.bee32.sem.hr.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.bee32.sem.misc.Contract;
import com.bee32.sem.people.entity.Person;

/**
 * 劳动合同
 *
 * @author jack
 */
@Entity
@DiscriminatorValue("LAB")
public class LaborContract
        extends Contract {

    private static final long serialVersionUID = 1L;

    Person employee;
    PersonResume employeeInfo;

    @ManyToOne
    public Person getEmployee() {
        return employee;
    }

    public void setEmployee(Person employee) {
        this.employee = employee;
    }

    @ManyToOne
    public PersonResume getEmployeeInfo() {
        return employeeInfo;
    }

    public void setEmployeeInfo(PersonResume employeeInfo) {
        this.employeeInfo = employeeInfo;
    }

}
