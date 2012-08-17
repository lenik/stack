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

    boolean present;

    // double bonus;

    public AttendanceType(String value, String name, boolean present) {
        super(value, name);
        this.present = present;
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

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
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

    public static final AttendanceType notAvailable = new AttendanceType("-", "notAvailable", false);
    public static final AttendanceType normal = new AttendanceType("NM", "normal", true);
    public static final AttendanceType late = new AttendanceType("LA", "late", false);
    public static final AttendanceType leave = new AttendanceType("LE", "leave", false);
    public static final AttendanceType sickLeave = new AttendanceType("SL", "sick-leave", false);
    public static final AttendanceType eveningOvertime = new AttendanceType("EO", "evening-overtime", true);
    public static final AttendanceType holidayOvertime = new AttendanceType("HO", "holiday-overtime", true);
    public static final AttendanceType restOvertime = new AttendanceType("RO", "rest-overtime", true);
    public static final AttendanceType trip = new AttendanceType("TR", "trip", true);
    public static final AttendanceType rest = new AttendanceType("RE", "rest", false);

}
