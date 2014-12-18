package com.bee32.zebra.oa.hr.impl;

import com.bee32.zebra.oa.hr.JobSkillCategory;
import com.bee32.zebra.tk.sea.FooManager;
import com.bee32.zebra.tk.sql.VhostDataService;

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

    JobSkillCategoryMapper mapper;

    public JobSkillCategoryManager() {
        super(JobSkillCategory.class);
        mapper = VhostDataService.getInstance().getMapper(JobSkillCategoryMapper.class);
    }

    public JobSkillCategoryMapper getMapper() {
        return mapper;
    }

}
