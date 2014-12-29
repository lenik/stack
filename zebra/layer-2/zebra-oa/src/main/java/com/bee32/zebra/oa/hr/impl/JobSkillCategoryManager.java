package com.bee32.zebra.oa.hr.impl;

import net.bodz.bas.rtx.IQueryable;

import com.bee32.zebra.oa.hr.JobSkillCategory;
import com.bee32.zebra.tk.sea.FooManager;

/**
 * 工作技能的分类。
 * 
 * @label 技能分类
 * 
 * @rel HREF1: TEXT1
 * 
 * @see <a href="HREF2">TEXT2</a>
 */
public class JobSkillCategoryManager
        extends FooManager {

    public JobSkillCategoryManager(IQueryable context) {
        super(JobSkillCategory.class, context);
    }

}
