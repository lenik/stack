package com.bee32.sem.salary.entity;

import com.bee32.plover.orm.sample.StandardSamples;

public class OvertimeTypes
        extends StandardSamples {

    public final OvertimeType normal = new OvertimeType("normal", "晚加班", 1, 1.0);
    public final OvertimeType rest = new OvertimeType("rest", "休息日加班", 2, 1.0);
    public final OvertimeType holiday = new OvertimeType("holiday", "节假日加班", 3, 1.0);

}
