package com.bee32.sem.chance.entity;

import com.bee32.plover.orm.sample.StandardSamples;

public class ProcurementMethods
        extends StandardSamples {

    public final ProcurementMethod JDJG = new ProcurementMethod("jdjg", "甲订甲供");
    public final ProcurementMethod JDYG = new ProcurementMethod("jdyg", "甲订乙供");
    public final ProcurementMethod YDYG = new ProcurementMethod("ydyg", "乙订乙供");
    public final ProcurementMethod OTHER = new ProcurementMethod("other", "其他");

}
