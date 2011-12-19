package com.bee32.sem.people.entity.personnel;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.bee32.sem.people.entity.Person;

/**
 * 内部员工/雇员
 *
 */
@Entity
@DiscriminatorValue("EMP")
public class Employee extends Person {

    private static final long serialVersionUID = 1L;

    int workingYears;
    PostTagname post;
    EducationTagname education;
    TitleTagname title;
    int duty;
    JobPerformanceTagname jobPerformance;
    int workAbility;
    Set<SkillTagname> skills = new HashSet<SkillTagname>();

    List<LaborContract> laborContracts;
    List<Resume> resumes;

    /**
     * 工龄
     * @return
     */
    public int getWorkingYears() {
        return workingYears;
    }

    public void setWorkingYears(int workingYears) {
        this.workingYears = workingYears;
    }

    public PostTagname getPost() {
        return post;
    }

    public void setPost(PostTagname post) {
        this.post = post;
    }

    public EducationTagname getEducation() {
        return education;
    }

    public void setEducation(EducationTagname education) {
        this.education = education;
    }

    public TitleTagname getTitle() {
        return title;
    }

    public void setTitle(TitleTagname title) {
        this.title = title;
    }

    /**
     * 考勤
     * @return
     */
    public int getDuty() {
        return duty;
    }

    public void setDuty(int duty) {
        this.duty = duty;
    }

    public JobPerformanceTagname getJobPerformance() {
        return jobPerformance;
    }

    public void setJobPerformance(JobPerformanceTagname jobPerformance) {
        this.jobPerformance = jobPerformance;
    }

    /**
     * 工作能力指数
     * @return
     */
    public int getWorkAbility() {
        return workAbility;
    }

    public void setWorkAbility(int workAbility) {
        this.workAbility = workAbility;
    }

    /**
     * 技能
     * @return
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
    public Set<SkillTagname> getSkills() {
        return skills;
    }

    public void setSkills(Set<SkillTagname> skills) {
        this.skills = skills;
    }

    /**
     * 劳动合同
     * @return
     */
    @OneToMany(mappedBy = "employee")
    public List<LaborContract> getLaborContracts() {
        return laborContracts;
    }

    public void setLaborContracts(List<LaborContract> laborContracts) {
        this.laborContracts = laborContracts;
    }

    /**
     * 履历
     * @return
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
    public List<Resume> getResumes() {
        return resumes;
    }

    public void setResumes(List<Resume> resumes) {
        this.resumes = resumes;
    }
}
