package com.bee32.sem.makebiz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.orm.entity.EntityAuto;
import com.bee32.sem.make.entity.QualityStandard;

/**
 * 质检
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "quality_check_seq", allocationSize = 1)
public class QualityCheck extends EntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    MakeStepInstance instance;

    QualityStandard standard;

    public static final int VALUE_LENGTH = 2000;

    String value;

    @ManyToOne(optional=false)
    public MakeStepInstance getInstance() {
        return instance;
    }

    public void setInstance(MakeStepInstance instance) {
        this.instance = instance;
    }

    @ManyToOne(optional=false)
    public QualityStandard getStandard() {
        return standard;
    }

    public void setStandard(QualityStandard standard) {
        this.standard = standard;
    }

    @Column(length=VALUE_LENGTH)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
