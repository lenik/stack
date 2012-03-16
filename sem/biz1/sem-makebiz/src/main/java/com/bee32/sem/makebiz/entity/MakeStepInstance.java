package com.bee32.sem.makebiz.entity;

import javax.persistence.Entity;

import com.bee32.plover.ox1.color.MomentInterval;
import com.bee32.sem.make.entity.MakeStep;

@Entity
public class MakeStepInstance
        extends MomentInterval {

    private static final long serialVersionUID = 1L;

    MakeProcessInstance instance;
    MakeStep model;

}
