package com.bee32.zebra.oa.hr;

import java.beans.Transient;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.bodz.bas.meta.bean.DetailLevel;
import net.bodz.bas.repr.form.meta.OfGroup;
import net.bodz.bas.repr.form.meta.StdGroup;
import net.bodz.bas.repr.form.meta.StdGroup.Schedule;
import net.bodz.lily.model.base.CoMomentInterval;
import net.bodz.lily.model.base.IdType;

import com.bee32.zebra.oa.contact.Person;

/**
 * 雇员信息
 * 
 * 公司雇员的关键信息。
 * 
 * <p lang="en">
 * Employee Information
 */
@IdType(Integer.class)
public class Employee
        extends CoMomentInterval<Integer> {

    private static final long serialVersionUID = 1L;

    Person person;

    BigDecimal baseSalary = BigDecimal.ZERO;
    JobPosition position;
    JobTitle title;
    EducationType education;
    int duty;
    int workAbility;
    BigDecimal pension = BigDecimal.ZERO;

    // List<LaborContract> laborContracts = new ArrayList<LaborContract>();
    List<EmployeeSkill> skills = new ArrayList<EmployeeSkill>();

    /**
     * 自然人
     * 
     * 雇员对应的一般自然人信息。
     */
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * 基本工资
     * 
     * 雇员的基本工资。
     */
    public BigDecimal getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(BigDecimal baseSalary) {
        this.baseSalary = baseSalary;
    }

    /**
     * 岗位
     * 
     * 雇员在公司中承担的岗位。
     */
    public JobPosition getPosition() {
        return position;
    }

    public void setPosition(JobPosition position) {
        this.position = position;
    }

    /**
     * 职称
     * 
     * 雇员在公司中的职称。
     */
    public JobTitle getTitle() {
        return title;
    }

    public void setTitle(JobTitle title) {
        this.title = title;
    }

    /**
     * 学历
     * 
     * 雇员的学历。
     */
    public EducationType getEducation() {
        return education;
    }

    public void setEducation(EducationType education) {
        this.education = education;
    }

    /**
     * 考勤
     * 
     * 雇员当月的出勤日数。
     */
    public int getDuty() {
        return duty;
    }

    public void setDuty(int duty) {
        this.duty = duty;
    }

    /**
     * 养老金
     * 
     * 雇员应缴纳的养老金。
     */
    public BigDecimal getPension() {
        return pension;
    }

    public void setPension(BigDecimal pension) {
        this.pension = pension;
    }

    /**
     * 雇佣日期
     * 
     * 雇员进入公司的日期。
     */
    @OfGroup(StdGroup.Schedule.class)
    public Date getEmployedDate() {
        return getBeginDate();
    }

    public void setEmployedDate(Date employedDate) {
        setBeginDate(employedDate);
    }

    /**
     * 解雇日期
     * 
     * 雇员离开公司的日期。
     */
    @OfGroup(StdGroup.Schedule.class)
    public Date getResignedDate() {
        return getEndDate();
    }

    public void setResignedDate(Date resignedDate) {
        setEndDate(resignedDate);
    }

    @DetailLevel(DetailLevel.HIDDEN)
    @OfGroup(Schedule.class)
    @Override
    public Date getBeginDate() {
        return super.getBeginDate();
    }

    @DetailLevel(DetailLevel.HIDDEN)
    @Override
    @OfGroup(Schedule.class)
    public Date getEndDate() {
        return super.getEndDate();
    }

    /**
     * 专业技能
     * 
     * 雇员掌握的一个或多个专业技能。
     */
    public List<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(List<EmployeeSkill> skills) {
        this.skills = skills;
    }

    /**
     * 工龄
     * 
     * 雇员的工作年限。
     */
    @Transient
    public int getWorkYears() {
        // if (currentDate > resignedDate) reutrn resignedDate - employedDate
        // else return currentDate - employedDate
        Date currentDate = new Date();
        Calendar current = Calendar.getInstance();
        current.setTime(currentDate);
        Calendar resigned = Calendar.getInstance();
        resigned.setTime(getResignedDate());
        Calendar employed = Calendar.getInstance();
        employed.setTime(getEmployedDate());

        // 计算方法:
        // 月数之差=年份相减*12+月份之差
        // 工龄(年)=月数之差/12

        Date resignedDate = getResignedDate();
        int months = 0;

        if (resignedDate != null && currentDate.after(resignedDate)) {
            months = (resigned.get(Calendar.YEAR) - employed.get(Calendar.YEAR)) * 12 //
                    + (resigned.get(Calendar.MONTH) - employed.get(Calendar.MONTH));

        } else {
            months = (current.get(Calendar.YEAR) - employed.get(Calendar.YEAR)) * 12 //
                    + (current.get(Calendar.MONTH) - employed.get(Calendar.MONTH));
        }

        return months / 12;
    }

}
