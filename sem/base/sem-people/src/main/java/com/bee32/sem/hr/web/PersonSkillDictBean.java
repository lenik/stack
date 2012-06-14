package com.bee32.sem.hr.web;

import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.hr.dto.PersonSkillCategoryDto;
import com.bee32.sem.hr.dto.PersonSkillCategoryLevelDto;
import com.bee32.sem.hr.entity.PersonSkillCategory;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class PersonSkillDictBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    PersonSkillCategoryLevelDto selectedLevel;

    public PersonSkillDictBean() {
        super(PersonSkillCategory.class, PersonSkillCategoryDto.class, 0);
    }

    /*************************************************************************
     * Section: MBeans
     *************************************************************************/
    final ListMBean<PersonSkillCategoryLevelDto> levelsMBean = ListMBean.fromEL(this, //
            "openedObject.levelList", PersonSkillCategoryLevelDto.class);

    public PersonSkillCategoryLevelDto getSelectedLevel() {
        return selectedLevel;
    }

    public void setSelectedLevel(PersonSkillCategoryLevelDto selectedLevel) {
        this.selectedLevel = selectedLevel;
    }

    public ListMBean<PersonSkillCategoryLevelDto> getLevelsMBean () {
        return levelsMBean;
    }
}
