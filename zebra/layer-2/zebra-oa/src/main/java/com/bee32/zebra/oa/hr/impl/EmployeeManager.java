package com.bee32.zebra.oa.hr.impl;

import com.bee32.zebra.oa.hr.Employee;
import com.bee32.zebra.tk.sea.FooManager;
import com.bee32.zebra.tk.sql.VhostDataService;

/**
 * 雇员的信息。
 * 
 * @label 雇员
 * 
 * @rel HREF1: TEXT1
 * 
 * @see <a href="HREF2">TEXT2</a>
 */
public class EmployeeManager
        extends FooManager {

    EmployeeMapper mapper;

    public EmployeeManager() {
        super(Employee.class);
        mapper = VhostDataService.getInstance().getMapper(EmployeeMapper.class);
    }

    public EmployeeMapper getMapper() {
        return mapper;
    }

}
