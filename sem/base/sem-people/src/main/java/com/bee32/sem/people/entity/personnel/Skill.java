package com.bee32.sem.people.entity.personnel;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.sem.base.tx.TxEntity;

/**
 * 员工所具有的技能
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "skill_seq", allocationSize = 1)
public class Skill extends TxEntity {

    private static final long serialVersionUID = 1L;

    SkillTagname skill;
    Employee employee;

    public SkillTagname getSkill() {
        return skill;
    }

    public void setSkill(SkillTagname skill) {
        this.skill = skill;
    }

    @ManyToOne
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

}
