package com.bee32.zebra.oa.hr.impl;

import com.bee32.zebra.oa.hr.Employee;
import com.bee32.zebra.tk.sea.FooVbo;

public class EmployeeVbo
        extends FooVbo<Employee> {

    public EmployeeVbo() {
        super(Employee.class);
    }

}
