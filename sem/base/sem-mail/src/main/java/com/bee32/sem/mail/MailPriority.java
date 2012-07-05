package com.bee32.sem.mail;

import java.util.Collection;

import com.bee32.plover.arch.util.EnumAlt;

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

    public static final MailPriority URGENT = new MailPriority(10, "urgent");
    public static final MailPriority NORMAL = new MailPriority(20, "normal");
    public static final MailPriority HIGH = new MailPriority(30, "high");
    public static final MailPriority LOW = new MailPriority(40, "low");

}