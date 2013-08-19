package com.bee32.sem.hr.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.DefaultValue;

import com.bee32.plover.orm.entity.CopyUtils;
import com.bee32.plover.ox1.color.UIEntityAuto;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.sem.people.entity.Person;

/**
 * 雇员信息
 *
 * 公司雇员的关键信息。
 *
 * <p lang="en">
 * Employee Information
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "employee_info_seq", allocationSize = 1)
public class EmployeeInfo
        extends UIEntityAuto<Long>
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    Person person;

    BigDecimal baseSalary = BigDecimal.ZERO;
    boolean motorist;
    JobPost role; // =predefined(JobPosts.class);
    JobTitle title;
    PersonEducationType education;
    int duty;
    int workAbility;
    BigDecimal pension = BigDecimal.ZERO;

    Date employedDate;
    Date resignedDate;

    List<LaborContract> laborContracts = new ArrayList<LaborContract>();
    List<PersonSkill> skills = new ArrayList<PersonSkill>();

    @Override
    public void populate(Object source) {
        if (source instanceof EmployeeInfo)
            _populate((EmployeeInfo) source);
        else
            super.populate(source);
    }

    protected void _populate(EmployeeInfo o) {
        super._populate(o);
        person = o.person;
        baseSalary = o.baseSalary;
        motorist = o.motorist;
        role = o.role;
        title = o.title;
        education = o.education;
        duty = o.duty;
        workAbility = o.workAbility;
        employedDate = o.employedDate;
        resignedDate = o.resignedDate;
        laborContracts = CopyUtils.copyList(o.laborContracts);
        skills = CopyUtils.copyList(o.skills);
    }

    /**
     * 自然人
     *
     * 雇员对应的一般自然人信息。
     */
    @ManyToOne
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
     * 驾驶员
     *
     * 表示雇员是否为汽车驾驶员。
     */
    @DefaultValue("false")
    public boolean isMotorist() {
        return motorist;
    }

    public void setMotorist(boolean motorist) {
        this.motorist = motorist;
    }

    /**
     * 岗位
     *
     * 雇员在公司中承担的岗位。
     */
    @ManyToOne
    public JobPost getRole() {
        return role;
    }

    public void setRole(JobPost role) {
        this.role = role;
    }

    /**
     * 职称
     *
     * 雇员在公司中的职称。
     */
    @ManyToOne
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
    @ManyToOne
    public PersonEducationType getEducation() {
        return education;
    }

    public void setEducation(PersonEducationType education) {
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
     * 工作能力指数
     *
     * 用于计算工资的工作能力指数。
     */
    public int getWorkAbility() {
        return workAbility;
    }

    public void setWorkAbility(int workAbility) {
        this.workAbility = workAbility;
    }

    /**
     * 养老金
     *
     * 雇员应缴纳的养老金。
     */
    @DefaultValue("0")
    @Column(nullable = false, scale = MONEY_ITEM_SCALE, precision = MONEY_ITEM_PRECISION)
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
    @Temporal(TemporalType.TIMESTAMP)
    public Date getEmployedDate() {
        return employedDate;
    }

    public void setEmployedDate(Date employedDate) {
        this.employedDate = employedDate;
    }

    /**
     * 解雇日期
     *
     * 雇员离开公司的日期。
     */
    @Temporal(TemporalType.TIMESTAMP)
    public Date getResignedDate() {
        return resignedDate;
    }

    public void setResignedDate(Date resignedDate) {
        this.resignedDate = resignedDate;
    }

    /**
     * 劳动合同
     *
     * 雇员和公司签订的一个或多个劳动合同。
     */
    @OneToMany(mappedBy = "employeeInfo", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<LaborContract> getLaborContracts() {
        return laborContracts;
    }

    public void setLaborContracts(List<LaborContract> laborContracts) {
        this.laborContracts = laborContracts;
    }

    /**
     * 专业技能
     *
     * 雇员掌握的一个或多个专业技能。
     */
    @OneToMany(mappedBy = "employeeInfo", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<PersonSkill> getSkills() {
        return skills;
    }

    public void setSkills(List<PersonSkill> skills) {
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
        resigned.setTime(resignedDate);
        Calendar employed = Calendar.getInstance();
        employed.setTime(employedDate);

        // 计算方法:
        // 月数之差=年份相减*12+月份之差
        // 工龄(年)=月数之差/12

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

    @Override
    protected void formatEntryText(StringBuilder buf) {
        if (person != null)
            buf.append(person.getDisplayName());
    }

}
