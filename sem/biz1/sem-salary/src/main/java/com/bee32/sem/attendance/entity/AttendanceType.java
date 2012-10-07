package com.bee32.sem.attendance.entity;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.bee32.plover.arch.util.EnumAlt;
import com.bee32.plover.arch.util.NoSuchEnumException;
import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.plover.site.SiteInstance;

public class AttendanceType
        extends EnumAlt<String, AttendanceType> {

    private static final long serialVersionUID = 1L;

    static final Map<String, AttendanceType> nameMap = new HashMap<String, AttendanceType>();
    static final Map<String, AttendanceType> valueMap = new HashMap<String, AttendanceType>();

    String icon;
    double hour;

    public AttendanceType(String value, String name, String icon, double hour) {
        super(value, name);
        this.icon = icon;
        this.hour = hour;
    }

    public static Collection<AttendanceType> values() {
        Collection<AttendanceType> values = valueMap.values();
        return Collections.unmodifiableCollection(values);
    }

    public static AttendanceType forName(String altName) {
        AttendanceType typeName = nameMap.get(altName);
        if (typeName == null)
            throw new NoSuchEnumException(AttendanceType.class, altName);
        return typeName;
    }

    public static AttendanceType forValue(String value) {
        if (value == null)
            return null;

        AttendanceType typeName = valueMap.get(value);
        if (typeName == null)
            throw new NoSuchEnumException(AttendanceType.class, value);

        return typeName;
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

    public static final AttendanceType notAvailable = new AttendanceType("-", "notAvailable", "", 0);
    public static final AttendanceType normal = new AttendanceType("NM", "normal", "", 4);
    public static final AttendanceType late = new AttendanceType("LA", "late", "", 3);
    public static final AttendanceType leave = new AttendanceType("LE", "leave", "", 3);
    public static final AttendanceType sickLeave = new AttendanceType("SL", "sick-leave", "", 3);
    public static final AttendanceType eveningOvertime = new AttendanceType("EO", "evening-overtime", "", 8);
    public static final AttendanceType holidayOvertime = new AttendanceType("HO", "holiday-overtime", "", 12);
    public static final AttendanceType restOvertime = new AttendanceType("RO", "rest-overtime", "", 8);
    public static final AttendanceType trip = new AttendanceType("TR", "trip", "", 4);
    public static final AttendanceType rest = new AttendanceType("RE", "rest", "", 0);
    public static final AttendanceType absent = new AttendanceType("AB", "absent", "", 0);

}
