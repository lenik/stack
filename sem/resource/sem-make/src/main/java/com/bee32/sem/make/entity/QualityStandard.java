package com.bee32.sem.make.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.orm.entity.EntityAuto;

/**
 * 质量标准
 */

@Entity
@SequenceGenerator(name = "idgen", sequenceName = "quality_standard_seq", allocationSize = 1)
public class QualityStandard extends EntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    public static final int NAME_LENGTH = 50;
    public static final int VALUE_LENGTH = 2000;

    MakeStep makeStep;

    String name;
    String value;


    @Override
    public void populate(Object source) {
        if (source instanceof QualityStandard)
            _populate((QualityStandard) source);
        else
            super.populate(source);
    }

    protected void _populate(QualityStandard o) {
        super._populate(o);
        makeStep = o.makeStep;
        name = o.name;
        value = o.value;
    }

    @ManyToOne(optional=false)
    public MakeStep getMakeStep() {
        return makeStep;
    }

    public void setMakeStep(MakeStep makeStep) {
        if (makeStep == null)
            throw new NullPointerException("makeStep");
        this.makeStep = makeStep;
    }

    @Column(length=NAME_LENGTH)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name == null)
            throw new NullPointerException("name");
        this.name = name;
    }

    @Column(length=VALUE_LENGTH)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
