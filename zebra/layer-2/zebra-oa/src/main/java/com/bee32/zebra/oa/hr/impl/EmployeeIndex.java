package com.bee32.zebra.oa.hr.impl;

import net.bodz.bas.meta.decl.ObjectType;
import net.bodz.bas.rtx.IQueryable;

import com.bee32.zebra.oa.hr.Employee;
import com.bee32.zebra.tk.sea.FooIndex;

/**
 * 雇员的信息。
 * 
 * @label 雇员
 * 
 * @rel HREF1: TEXT1
 * 
 * @see <a href="HREF2">TEXT2</a>
 */
@ObjectType(Employee.class)
public class EmployeeIndex
        extends FooIndex {

    public EmployeeIndex(IQueryable context) {
        super(context);
    }

}
