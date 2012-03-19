package com.bee32.sem.makebiz.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.orm.entity.EntityAuto;
import com.bee32.sem.people.entity.Person;

/**
 * 工艺操作人
 *
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "make_step_operator_seq", allocationSize = 1)
public class MakeStepOperator extends EntityAuto<Long>  {

    private static final long serialVersionUID = 1L;

    MakeStepInstance instance;
    Person person;

    @NaturalId
    @ManyToOne(optional=false)
    public MakeStepInstance getInstance() {
        return instance;
    }

    public void setInstance(MakeStepInstance instance) {
        this.instance = instance;
    }

    @NaturalId
    @ManyToOne(optional=false)
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

}
