package com.bee32.sem.hr.web;

import com.bee32.sem.hr.dto.PersonSkillCategoryDto;
import com.bee32.sem.hr.entity.PersonSkillCategory;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class PersonSkillDictBean extends SimpleEntityViewBean{

    private static final long serialVersionUID = 1L;

    public PersonSkillDictBean() {
        super(PersonSkillCategory.class, PersonSkillCategoryDto.class, 0);
    }

}
