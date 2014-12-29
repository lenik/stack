package com.bee32.zebra.oa.hr.impl;

import net.bodz.bas.rtx.IQueryable;

import com.bee32.zebra.oa.hr.Employee;
import com.bee32.zebra.tk.sea.FooManager;

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

    public EmployeeManager(IQueryable context) {
        super(Employee.class, context);
    }

}
