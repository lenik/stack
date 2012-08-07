package com.bee32.sem.hr.entity;

import java.math.BigDecimal;

import com.bee32.plover.orm.sample.StandardSamples;

public class PersonEducationTypes
        extends StandardSamples {
    public final PersonEducationType primary = new PersonEducationType("primary", "小学", new BigDecimal(0));
    public final PersonEducationType junior = new PersonEducationType("junior", "初中", new BigDecimal(0));
    public final PersonEducationType high = new PersonEducationType("high", "高中", new BigDecimal(10));
    public final PersonEducationType college = new PersonEducationType("college", "本科", new BigDecimal(100));
    public final PersonEducationType master = new PersonEducationType("master", "硕士", new BigDecimal(1000));

}
