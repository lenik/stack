package com.bee32.sem.attendance.entity;

import java.io.IOException;
import java.util.Collection;

import com.bee32.plover.arch.util.EnumAlt;
import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.plover.site.SiteInstance;

public class AttendanceType
        extends EnumAlt<Character, AttendanceType> {

    private static final long serialVersionUID = 1L;

    String icon;
    double hour;

    public AttendanceType(Character value, String name, String icon, double hour) {
        super(value, name);
        this.icon = icon;
        this.hour = hour;
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

    public double getHour() {
        return hour;
    }

    public double getBonus() {
        SiteInstance site = ThreadHttpContext.getSiteInstance();
        String str = site.getProperty("a-bonus");

        str.indexOf(getName() + "=");
        return 0;
    }

    public void setBonus(double bonus) {
        SiteInstance site = ThreadHttpContext.getSiteInstance();
        // script = ...
        // site.setProperty("a-bonus", script);
    }

    public void saveBonuses()
            throws IOException {
        SiteInstance site = ThreadHttpContext.getSiteInstance();
        site.saveConfig();
    }

    public static final AttendanceType notAvailable = new AttendanceType('-', "notAvailable", "", 0);
    public static final AttendanceType normal = new AttendanceType('A', "normal", "", 4);
    public static final AttendanceType late = new AttendanceType('B', "late", "", 3);
    public static final AttendanceType leave = new AttendanceType('C', "leave", "", 3);
    public static final AttendanceType sickLeave = new AttendanceType('D', "sick-leave", "", 3);
    public static final AttendanceType eveningOvertime = new AttendanceType('E', "evening-overtime", "", 8);
    public static final AttendanceType holidayOvertime = new AttendanceType('F', "holiday-overtime", "", 12);
    public static final AttendanceType restOvertime = new AttendanceType('G', "rest-overtime", "", 8);
    public static final AttendanceType trip = new AttendanceType('H', "trip", "", 4);
    public static final AttendanceType rest = new AttendanceType('I', "rest", "", 0);
    public static final AttendanceType absent = new AttendanceType('J', "absent", "", 0);

}
