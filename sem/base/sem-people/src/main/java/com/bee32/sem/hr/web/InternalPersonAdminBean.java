package com.bee32.sem.hr.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.hr.dto.EmployeeInfoDto;
import com.bee32.sem.hr.dto.PersonSkillCategoryLevelDto;
import com.bee32.sem.hr.dto.PersonSkillDto;
import com.bee32.sem.hr.entity.EmployeeInfo;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.people.dto.PersonDto;
import com.bee32.sem.people.entity.Person;
import com.bee32.sem.people.util.PeopleCriteria;

@ForEntity(EmployeeInfo.class)
public class InternalPersonAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    List<PersonDto> internalPersons;
    List<Integer> selectedLevels;

    public InternalPersonAdminBean() {
        super(EmployeeInfo.class, EmployeeInfoDto.class, 0, Order.desc("id"), PeopleCriteria.listEmployeeInfo());
        internalPersons = DTOs.marshalList(PersonDto.class,
                ctx.data.access(Person.class).list(PeopleCriteria.internal()));
    }

    public void doSelectSkill() {
        EmployeeInfoDto info = getOpenedObject();
        if (selectedLevels.isEmpty()) {
            uiLogger.error("没有选择员工技能");
            return;
        }
        List<PersonSkillDto> selected = new ArrayList<PersonSkillDto>();
        Map<String, PersonSkillCategoryLevelDto> map = new HashMap<String, PersonSkillCategoryLevelDto>();
        for (int levelId : selectedLevels) {
            for (PersonSkillCategoryLevelDto level : EmployeeDictsBean.skillLevels) {
                if (level.getId() == levelId) {
                    if (map.get(level.getCategory().getId()) != null) {
                        PersonSkillCategoryLevelDto cachedlevelDto = map.get(level.getCategory().getId());
                        if (level.getScore() > cachedlevelDto.getScore())
                            map.put(level.getCategory().getId(), level);
                    } else
                        map.put(level.getCategory().getId(), level);
                }
            }
        }
        StringBuffer sb = new StringBuffer();
        for (PersonSkillCategoryLevelDto level : map.values()) {
            selected.add(level.toPersonSkillCategory(info));
            if (sb.length() == 0)
                sb.append(level.getLabel());
            else
                sb.append("," + level.getLabel());
        }

        info.setSelectedLevels(selectedLevels);
        info.setSkillData(sb.toString());
        info.setSkills(selected);
    }

    public List<PersonDto> getInternalPersons() {
        return internalPersons;
    }

    public List<Integer> getSelectedLevels() {
        return selectedLevels;
    }

    public void setSelectedLevels(List<Integer> selectedLevels) {
        this.selectedLevels = selectedLevels;
    }

}
