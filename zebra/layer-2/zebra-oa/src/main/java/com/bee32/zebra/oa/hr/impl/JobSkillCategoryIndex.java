package com.bee32.zebra.oa.hr.impl;

import net.bodz.bas.meta.decl.ObjectType;
import net.bodz.bas.rtx.IQueryable;

import com.bee32.zebra.oa.hr.JobSkillCategory;
import com.bee32.zebra.tk.sea.FooIndex;

/**
 * 工作技能的分类。
 * 
 * @label 技能分类
 * 
 * @rel HREF1: TEXT1
 * 
 * @see <a href="HREF2">TEXT2</a>
 */
@ObjectType(JobSkillCategory.class)
public class JobSkillCategoryIndex
        extends FooIndex {

    public JobSkillCategoryIndex(IQueryable context) {
        super(context);
    }

}
