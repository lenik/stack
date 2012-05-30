package com.bee32.sem.hr.entity;

import com.bee32.plover.orm.sample.StandardSamples;

public class JobTitles extends StandardSamples {

    public final JobTitle technician = new JobTitle("technician", "技士");
    public final JobTitle competentTechnician = new JobTitle("competentTechnician", "主管技师");
    public final JobTitle deputyDirectorTechnician  = new JobTitle("deputyDirectorTechnician", "副主任技师");
    public final JobTitle directorTechnician  = new JobTitle("directorTechnician", "主任技师");

}
