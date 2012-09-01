package com.bee32.sem.hr.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.free.ParseException;
import javax.validation.constraints.Future;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.hr.entity.EmployeeInfo;
import com.bee32.sem.hr.entity.LaborContract;
import com.bee32.sem.people.dto.PersonDto;

public class EmployeeInfoDto
        extends UIEntityDto<EmployeeInfo, Long> {

    private static final long serialVersionUID = 1L;

    PersonDto person;

    BigDecimal baseSalary;
    boolean motorist;
    JobPostDto role;
    JobTitleDto title;
// JobPerformanceDto jobPerformance;
    PersonEducationTypeDto education;
    int duty;
    int workAbility;

    Date employedDate;
    Date resignedDate;

    List<LaborContract> laborContracts = new ArrayList<LaborContract>();
    List<PersonSkillDto> skills = new ArrayList<PersonSkillDto>();

// List<PersonSkillCategoryLevelDto> skillLevels = new ArrayList<PersonSkillCategoryLevelDto>();
    List<Integer> selectedLevels = new ArrayList<Integer>();
    String skillData = "N/A";

    public EmployeeInfoDto() {
        super();
    }

    public EmployeeInfoDto(int fmask) {
        super(fmask);
    }

    @Override
    protected void _marshal(EmployeeInfo source) {
        person = mref(PersonDto.class, source.getPerson());
        baseSalary = source.getBaseSalary();
        motorist = source.isMotorist();
        role = mref(JobPostDto.class, source.getRole());
        title = mref(JobTitleDto.class, source.getTitle());
// jobPerformance = mref(JobPerformanceDto.class, source.getJobPerformance());
        education = mref(PersonEducationTypeDto.class, source.getEducation());
        duty = source.getDuty();
        workAbility = source.getWorkAbility();
        employedDate = source.getEmployedDate();
        resignedDate = source.getResignedDate();
        skills = mrefList(PersonSkillDto.class, source.getSkills());
        StringBuffer sb = new StringBuffer();
        for (PersonSkillDto skill : skills) {
            int score = skill.getScore();
// selectedLevels.add(skill.getCategory().getId() + ":" + skill.getScore() + ":" +
// skill.getLevelLabel(score));
            if (sb.length() == 0)
                sb.append(skill.getLevelLabel(score));
            else
                sb.append("," + skill.getLevelLabel(score));
        }
        if (sb.length() > 0)
            skillData = sb.toString();

    }

    @Override
    protected void _unmarshalTo(EmployeeInfo target) {
        merge(target, "person", person);
        target.setBaseSalary(baseSalary);
        target.setMotorist(motorist);
        merge(target, "role", role);
        if (title.getId() != -1)
            merge(target, "title", title);
        if (education.getId() != -1)
            merge(target, "education", education);
        target.setDuty(duty);
        target.setWorkAbility(workAbility);
        target.setEmployedDate(employedDate);
        target.setResignedDate(resignedDate);
        mergeList(target, "skills", skills);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public PersonDto getPerson() {
        return person;
    }

    public void setPerson(PersonDto person) {
        this.person = person;
    }

    public String getPersonName() {
        if (person == null)
            return "";
        else
            return person.getName();
    }

    public BigDecimal getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(BigDecimal baseSalary) {
        this.baseSalary = baseSalary;
    }

    public boolean isMotorist() {
        return motorist;
    }

    public void setMotorist(boolean motorist) {
        this.motorist = motorist;
    }

    public JobPostDto getRole() {
        return role;
    }

    public void setRole(JobPostDto role) {
        this.role = role;
    }

    public JobTitleDto getTitle() {
        return title;
    }

    public void setTitle(JobTitleDto title) {
        this.title = title;
    }

// public JobPerformanceDto getJobPerformance() {
// return jobPerformance;
// }
//
// public void setJobPerformance(JobPerformanceDto jobPerformance) {
// this.jobPerformance = jobPerformance;
// }

    public PersonEducationTypeDto getEducation() {
        return education;
    }

    public void setEducation(PersonEducationTypeDto education) {
        this.education = education;
    }

    public int getDuty() {
        return duty;
    }

    public void setDuty(int duty) {
        this.duty = duty;
    }

    public int getWorkAbility() {
        return workAbility;
    }

    public void setWorkAbility(int workAbility) {
        this.workAbility = workAbility;
    }

    public Date getEmployedDate() {
        return employedDate;
    }

    public void setEmployedDate(Date employedDate) {
        this.employedDate = employedDate;
    }

    @Future
    public Date getResignedDate() {
        return resignedDate;
    }

    public void setResignedDate(Date resignedDate) {
        this.resignedDate = resignedDate;
    }

    public List<LaborContract> getLaborContracts() {
        return laborContracts;
    }

    public void setLaborContracts(List<LaborContract> laborContracts) {
        this.laborContracts = laborContracts;
    }

    public List<PersonSkillDto> getSkills() {
        return skills;
    }

    public void setSkills(List<PersonSkillDto> skills) {
        this.skills = skills;
    }

    public String getSkillData() {
        return skillData;
    }

    public List<Integer> getSelectedLevels() {
        return selectedLevels;
    }

    public void setSelectedLevels(List<Integer> selectedLevels) {
        this.selectedLevels = selectedLevels;
    }

    public void setSkillData(String skillData) {
        this.skillData = skillData;
    }

}
