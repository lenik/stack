package com.bee32.sem.attendance.entity;

import javax.persistence.Entity;

import com.bee32.plover.ox1.dict.ShortNameDict;

@Entity
public class AttendanceType
        extends ShortNameDict {

    private static final long serialVersionUID = 1L;

    boolean attendance;

    public AttendanceType() {
    }

    public AttendanceType(String name, String label, boolean attendance) {
        super(name, label);
        this.attendance = attendance;
    }

    public boolean isAttendance() {
        return attendance;
    }

    public void setAttendance(boolean attendance) {
        this.attendance = attendance;
    }

}
