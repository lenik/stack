package com.bee32.sem.hr.entity;

import java.math.BigDecimal;

import com.bee32.plover.orm.sample.NormalSamples;

public class JobTitles
        extends NormalSamples {

    public final JobTitle technician = new JobTitle("技士", new BigDecimal(150));
    /* competentTechnician */
    public final JobTitle competent = new JobTitle("主管技师", new BigDecimal(300));
    /* deputyDirectorTechnician */
    public final JobTitle deputyDirector = new JobTitle("副主任技师", new BigDecimal(500));
    /* directorTechnician */
    public final JobTitle directorTechnician = new JobTitle("主任技师", new BigDecimal(1000));

}
