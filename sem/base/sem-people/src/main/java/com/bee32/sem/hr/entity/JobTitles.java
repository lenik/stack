package com.bee32.sem.hr.entity;

import com.bee32.plover.orm.sample.StandardSamples;

public class JobTitles extends StandardSamples {

    public final JobTitle technician = new JobTitle("technician", "技士");
    /* competentTechnician */
    public final JobTitle competent = new JobTitle("competent", "主管技师");
    /* deputyDirectorTechnician */
    public final JobTitle deputyDirector = new JobTitle("deputyDirector", "副主任技师");
    /* directorTechnician */
    public final JobTitle directorTechnician = new JobTitle("director", "主任技师");

}
