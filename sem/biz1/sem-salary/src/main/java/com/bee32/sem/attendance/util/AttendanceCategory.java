package com.bee32.sem.attendance.util;

import java.util.Collection;

import com.bee32.plover.arch.util.EnumAlt;
import com.bee32.sem.attendance.entity.AttendanceType;

public class AttendanceCategory
        extends EnumAlt<Character, AttendanceCategory> {

    private static final long serialVersionUID = 1L;

    public AttendanceCategory(Character value, String name) {
        super(value, name);
    }

    public static AttendanceType forName(String name) {
        return forName(AttendanceType.class, name);
    }

    public static Collection<AttendanceType> values() {
        return values(AttendanceType.class);
    }

    public static AttendanceType forValue(Character value) {
        return forValue(AttendanceType.class, value);
    }

    public static AttendanceType forValue(char value) {
        return forValue(new Character(value));
    }

    public static final AttendanceCategory category1 = new AttendanceCategory('*', "正常出勤"); // 正常出勤
    public static final AttendanceCategory category2 = new AttendanceCategory('+', "加班"); // 加班
    public static final AttendanceCategory category3 = new AttendanceCategory('-', "节日"); // 节日
    public static final AttendanceCategory category4 = new AttendanceCategory('=', "假日"); // 假日

}
