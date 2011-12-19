package com.bee32.sem.hr.service;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.arch.DataService;
import com.bee32.sem.people.entity.Person;

public class SalaryCalculator
        extends DataService
        implements ISalaryCalculator {

    @Transactional(readOnly = true)
    @Override
    public double getPreferredSalary(Person person) {
        return 0;
    }

}
