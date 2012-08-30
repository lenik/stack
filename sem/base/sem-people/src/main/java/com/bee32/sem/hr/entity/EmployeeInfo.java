package com.bee32.sem.hr.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import com.bee32.sem.people.entity.Person;

@Entity
@SequenceGenerator(name = "idgen", sequenceName = "employee_info_seq", allocationSize = 1)
public class EmployeeInfo
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    Person person;

    BigDecimal baseSalary = new BigDecimal(0);
    boolean motorist;
    JobPost role; // =predefined(JobPosts.class);
    JobTitle title;
    PersonEducationType education;
    int duty;
    int workAbility;

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
//        jobPerformance = o.jobPerformance;
        education = o.education;
        duty = o.duty;
        workAbility = o.workAbility;
        employedDate = o.employedDate;
        resignedDate = o.resignedDate;
        laborContracts = CopyUtils.copyList(o.laborContracts);
        skills = CopyUtils.copyList(o.skills);
    }

    @ManyToOne
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public BigDecimal getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(BigDecimal baseSalary) {
        this.baseSalary = baseSalary;
    }

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
     * @return
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
     * @return
     */
    @ManyToOne
    public JobTitle getTitle() {
        return title;
    }

    public void setTitle(JobTitle title) {
        this.title = title;
    }

    /**
     * 月度工作表现
     *
     * @return
     */
//    @ManyToOne
//    public JobPerformance getJobPerformance() {
//        return jobPerformance;
//    }
//
//    public void setJobPerformance(JobPerformance jobPerformance) {
//        this.jobPerformance = jobPerformance;
//    }

    /**
     * 学历
     *
     * @return
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
     * @return
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
     * @return
     */
    public int getWorkAbility() {
        return workAbility;
    }

    public void setWorkAbility(int workAbility) {
        this.workAbility = workAbility;
    }

    /**
     * 入职日期
     *
     * @return
     */
    @Temporal(TemporalType.TIMESTAMP)
    public Date getEmployedDate() {
        return employedDate;
    }

    public void setEmployedDate(Date employedDate) {
        this.employedDate = employedDate;
    }

    /**
     * 离职日期
     *
     * @return
     */
    @Temporal(TemporalType.TIMESTAMP)
    public Date getResignedDate() {
        return resignedDate;
    }

    public void setResignedDate(Date resignedDate) {
        this.resignedDate = resignedDate;
    }

    /**
     * 劳动合同(多个)
     *
     * @return
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
     * 专业技能(多个)
     *
     * @return
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
     * @return
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

}
