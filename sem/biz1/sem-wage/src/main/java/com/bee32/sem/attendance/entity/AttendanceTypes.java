package com.bee32.sem.attendance.entity;

import com.bee32.plover.orm.sample.StandardSamples;

public class AttendanceTypes
        extends StandardSamples {

    public final AttendanceType normal = new AttendanceType("normal", "正常出勤", true);
    public final AttendanceType leave = new AttendanceType("leave", "请假", false);
    public final AttendanceType absent = new AttendanceType("absent", "旷工", false);
    public final AttendanceType rest = new AttendanceType("rest", "休息", false);
    public final AttendanceType overtime = new AttendanceType("overtime", "加班", true);
    public final AttendanceType hot = new AttendanceType("holiday", "假日加班", true);
}
