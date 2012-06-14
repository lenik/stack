package com.bee32.sem.hr.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.free.ParseException;
import javax.validation.constraints.Past;

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
        role = mref(JobPostDto.class, source.getRole());
        title = mref(JobTitleDto.class, source.getTitle());
        jobPerformance = mref(JobPerformanceDto.class, source.getJobPerformance());
        education = mref(PersonEducationTypeDto.class, source.getEducation());
        duty = source.getDuty();
        workAbility = source.getWorkAbility();
        employedDate = source.getEmployedDate();
        resignedDate = source.getResignedDate();
    }

    @Override
    protected void _unmarshalTo(EmployeeInfo target) {
        merge(target, "person", person);
        merge(target, "role", role);
        merge(target, "title", title);
        merge(target, "jobPerformance", jobPerformance);
        target.setDuty(duty);
        target.setWorkAbility(workAbility);
        target.setEmployedDate(employedDate);
        target.setResignedDate(resignedDate);
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

    public JobPerformanceDto getJobPerformance() {
        return jobPerformance;
    }

    public void setJobPerformance(JobPerformanceDto jobPerformance) {
        this.jobPerformance = jobPerformance;
    }

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

    @Past
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

    public List<PersonSkill> getSkills() {
        return skills;
    }

    public void setSkills(List<PersonSkill> skills) {
        this.skills = skills;
    }

}
