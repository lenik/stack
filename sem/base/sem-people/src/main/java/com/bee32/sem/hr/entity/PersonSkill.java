package com.bee32.sem.hr.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import com.bee32.sem.base.tx.TxEntity;

/**
 * 员工所具有的技能
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "skill_seq", allocationSize = 1)
public class PersonSkill
        extends TxEntity {

    private static final long serialVersionUID = 1L;

    SkillCategory category;
    int score;

    public SkillCategory getCategory() {
        return category;
    }

    public void setCategory(SkillCategory category) {
        this.category = category;
    }

    /**
     * @see SkillCategory#getLevelMap()
     */
    @Column(nullable = false)
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
