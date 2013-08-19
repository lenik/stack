package com.bee32.sem.attendance.entity;

import java.io.IOException;
import java.util.Collection;

import com.bee32.plover.arch.util.EnumAlt;
import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.plover.site.SiteInstance;

/**
 * 出勤类型
 *
 * <p lang="en">
 * Attendance Type
 */
public class AttendanceType
        extends EnumAlt<Character, AttendanceType> {

    private static final long serialVersionUID = 1L;

    String iconString;
    boolean multiplier;

    public AttendanceType(Character value, String name, String iconString, boolean multiplier) {
        super(value, name);
        this.iconString = iconString;
        this.multiplier = multiplier;
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

    public String getIconString() {
        return iconString;
    }

    public boolean isMutiplier() {
        return multiplier;
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

    public static final AttendanceType notAvailable = new AttendanceType('-', "不可用", "notavailable.jpg", false);

    /**
     * 正常出勤
     */
    public static final AttendanceType normal = new AttendanceType('A', "出勤", "attendance.jpg", true); // 正常出勤
    public static final AttendanceType trip = new AttendanceType('B', "出差", "trip.jpg", true); // 外勤

    /**
     * 缺勤：请假
     */
    public static final AttendanceType leave1 = new AttendanceType('C', "请假", "aleave.jpg", false); // 一般请假
    public static final AttendanceType leave2 = new AttendanceType('D', "婚假", "mleave.jpg", false); // 婚假
    public static final AttendanceType leave3 = new AttendanceType('E', "产假", "home.jpg", false); // 产假

    /**
     * 缺勤：其他
     */
    public static final AttendanceType late1 = new AttendanceType('F', "迟到", "late.jpg", true); // 迟到

    /**
     * 缺勤：矿工
     */
    public static final AttendanceType late2 = new AttendanceType('G', "矿工", "error.jpg", false); // 矿工

    /**
     * 应加班
     */
    public static final AttendanceType over1 = new AttendanceType('H', "晚加班", "star.jpg", true); // 晚加班
    public static final AttendanceType over2 = new AttendanceType('I', "节假日加班", "flag.jpg", true); // 节日加班
    public static final AttendanceType over4 = new AttendanceType('J', "休息日加班", "hstar.jpg", true); // 休息日加班

    /**
     * 其他
     */
    public static final AttendanceType rest2 = new AttendanceType('Z', "公休", "rest.jpg", false); // 公休

}
