package com.bee32.sem.wage.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;

import com.bee32.plover.ox1.dict.ShortNameDict;
import com.bee32.sem.hr.entity.PersonEducationType;

/**
 * 学历补贴
 */
@Entity
public class EducationSubsidy
        extends ShortNameDict {

    private static final long serialVersionUID = 1L;

    PersonEducationType educationType;
    BigDecimal subsidy;

    public EducationSubsidy(String name, String label, String description, PersonEducationType educationType,
            BigDecimal subsidy) {
        super(name, label, description);
        this.educationType = educationType;
        this.subsidy = subsidy;
    }

    public PersonEducationType getEducationType() {
        return educationType;
    }

    public void setEducationType(PersonEducationType educationType) {
        this.educationType = educationType;
    }

    public BigDecimal getSubsidy() {
        return subsidy;
    }

    public void setSubsidy(BigDecimal subsidy) {
        this.subsidy = subsidy;
    }

}
