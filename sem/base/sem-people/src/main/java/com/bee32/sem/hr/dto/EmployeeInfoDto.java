package com.bee32.sem.hr.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.hr.entity.EmployeeInfo;
import com.bee32.sem.hr.entity.LaborContract;
import com.bee32.sem.hr.entity.PersonSkill;
import com.bee32.sem.people.dto.PersonDto;

public class EmployeeInfoDto
        extends UIEntityDto<EmployeeInfo, Long> {

    private static final long serialVersionUID = 1L;

    PersonDto person;

    JobPostDto role;
    JobTitleDto title;
    JobPerformanceDto jobPerformance;
    PersonEducationTypeDto education;
    int duty;
    int workAbility;

    Date employedDate;
    Date resignedDate;

    List<LaborContract> laborContracts = new ArrayList<LaborContract>();
    List<PersonSkill> skills = new ArrayList<PersonSkill>();

    public EmployeeInfoDto() {
        super();
    }

    public EmployeeInfoDto(int fmask) {
        super(fmask);
    }

    @Override
    protected void _marshal(EmployeeInfo source) {
        person = mref(PersonDto.class, source.getPerson());
    }

    @Override
    protected void _unmarshalTo(EmployeeInfo target) {
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

}
