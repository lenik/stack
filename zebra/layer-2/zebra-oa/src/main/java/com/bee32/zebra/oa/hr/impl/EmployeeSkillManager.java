package com.bee32.zebra.oa.hr.impl;

import com.bee32.zebra.oa.hr.EmployeeSkill;
import com.bee32.zebra.tk.sea.FooManager;
import com.bee32.zebra.tk.sql.VhostDataService;

/**
 * 专项技能定义。
 * 
 * @label 工作技能
 * 
 * @rel HREF1: TEXT1
 * 
 * @see <a href="HREF2">TEXT2</a>
 */
public class EmployeeSkillManager
        extends FooManager {

    EmployeeSkillMapper mapper;

    public EmployeeSkillManager() {
        super(EmployeeSkill.class);
        mapper = VhostDataService.getInstance().getMapper(EmployeeSkillMapper.class);
    }

    public EmployeeSkillMapper getMapper() {
        return mapper;
    }

}
