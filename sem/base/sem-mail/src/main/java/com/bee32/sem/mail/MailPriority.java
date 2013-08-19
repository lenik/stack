package com.bee32.sem.mail;

import java.util.Collection;

import com.bee32.plover.arch.util.EnumAlt;

/**
 * 邮件等级
 *
 * <p lang="en">
 * Mail Priority
 */
public class MailPriority
        extends EnumAlt<Integer, MailPriority> {

    private static final long serialVersionUID = 1L;

    public MailPriority(int priority, String name) {
        super(priority, name);
    }

    public static MailPriority forName(String name) {
        return forName(MailPriority.class, name);
    }

    public static Collection<MailPriority> values() {
        return values(MailPriority.class);
    }

    public static MailPriority forValue(Integer value) {
        return forValue(MailPriority.class, value);
    }

    /**
     * 紧急
     *
     * <p lang="en">
     * Urgent
     */
    public static final MailPriority URGENT = new MailPriority(10, "urgent");

    /**
     * 正常
     *
     * <p lang="en">
     * Normal
     */
    public static final MailPriority NORMAL = new MailPriority(20, "normal");

    /**
     * 高
     *
     * <p lang="en">
     * High
     */
    public static final MailPriority HIGH = new MailPriority(30, "high");

    /**
     * 低
     *
     * <p lang="en">
     * Low
     */
    public static final MailPriority LOW = new MailPriority(40, "low");

}