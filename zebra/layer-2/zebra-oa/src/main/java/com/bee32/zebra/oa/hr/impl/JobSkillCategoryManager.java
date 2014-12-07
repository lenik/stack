package com.bee32.zebra.oa.hr.impl;

import com.bee32.zebra.tk.sql.VhostDataService;
import com.tinylily.repr.CoEntityManager;

/**
 * TITLE
 * 
 * @label LABEL
 * 
 * @rel HREF1: TEXT1
 * 
 * @see <a href="HREF2">TEXT2</a>
 */
public class JobSkillCategoryManager
        extends CoEntityManager {

    JobSkillCategoryMapper mapper;

    public JobSkillCategoryManager() {
        mapper = VhostDataService.getInstance().getMapper(JobSkillCategoryMapper.class);
    }

    public JobSkillCategoryMapper getMapper() {
        return mapper;
    }

}
