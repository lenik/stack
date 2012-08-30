package com.bee32.sem.hr.entity;

import java.math.BigDecimal;

import com.bee32.plover.orm.sample.NormalSamples;

public class PersonEducationTypes
        extends NormalSamples {

    public final PersonEducationType primary = new PersonEducationType("小学", new BigDecimal(0));
    public final PersonEducationType junior = new PersonEducationType("初中", new BigDecimal(0));
    public final PersonEducationType high = new PersonEducationType("高中", new BigDecimal(10));
    public final PersonEducationType college = new PersonEducationType("本科", new BigDecimal(100));
    public final PersonEducationType master = new PersonEducationType("硕士", new BigDecimal(1000));

}
