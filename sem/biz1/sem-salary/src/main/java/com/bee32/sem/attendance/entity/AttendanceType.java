package com.bee32.sem.attendance.entity;

import java.io.IOException;
import java.util.Collection;

import com.bee32.plover.arch.util.EnumAlt;
import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.plover.site.SiteInstance;

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

    public static final AttendanceType notAvailable = new AttendanceType('-', "不可用", "div.jpg", false);

    /**
     * 正常出勤
     */
    public static final AttendanceType normal = new AttendanceType('A', "内勤", "right.jpg", true); // 正常出勤
    public static final AttendanceType trip = new AttendanceType('B', "外勤", "right.jpg", true); // 外勤

    /**
     * 缺勤：请假
     */
    public static final AttendanceType leave1 = new AttendanceType('C', "请假", "rabbish.jpg", false); // 一般请假
    public static final AttendanceType leave2 = new AttendanceType('D', "婚假", "rabbish.jpg", false); // 婚假
    public static final AttendanceType leave3 = new AttendanceType('E', "产假", "rabbish.jpg", false); // 产假

    /**
     * 缺勤：其他
     */
    public static final AttendanceType late1 = new AttendanceType('F', "迟到", "leave.jpg", true); // 迟到

    /**
     * 缺勤：矿工
     */
    public static final AttendanceType late2 = new AttendanceType('G', "矿工", "error.jpg", false); // 矿工

    /**
     * 应加班
     */
    public static final AttendanceType over1 = new AttendanceType('H', "晚加班", "star.jpg", true); // 晚加班
    public static final AttendanceType over2 = new AttendanceType('I', "节日加班", "star.jpg", true); // 节日加班
    public static final AttendanceType over3 = new AttendanceType('J', "假日加班", "star.jpg", true); // 假日加班
    public static final AttendanceType over4 = new AttendanceType('K', "休息日加班", "star.jpg", true); // 休息日加班

    /**
     * 其他
     */
    public static final AttendanceType rest1 = new AttendanceType('Y', "不加班", "stop.jpg", false); // 不应晚加班
    public static final AttendanceType rest2 = new AttendanceType('Z', "公休", "stop.jpg", false); // 公休

}
