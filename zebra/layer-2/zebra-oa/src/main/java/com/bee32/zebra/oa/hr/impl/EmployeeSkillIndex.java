package com.bee32.zebra.oa.hr.impl;

import net.bodz.bas.meta.decl.ObjectType;
import net.bodz.bas.rtx.IQueryable;

import com.bee32.zebra.oa.hr.EmployeeSkill;
import com.bee32.zebra.tk.sea.FooIndex;

/**
 * 专项技能定义。
 * 
 * @label 工作技能
 * 
 * @rel HREF1: TEXT1
 * 
 * @see <a href="HREF2">TEXT2</a>
 */
@ObjectType(EmployeeSkill.class)
public class EmployeeSkillIndex
        extends FooIndex {

    public EmployeeSkillIndex(IQueryable context) {
        super(context);
    }

}
