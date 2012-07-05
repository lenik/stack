package com.bee32.sem.mail.entity;

import java.util.Collection;

import com.bee32.plover.arch.util.EnumAlt;

public class MailOrientation
        extends EnumAlt<Integer, MailOrientation> {

    private static final long serialVersionUID = 1L;

    public MailOrientation(int value, String name) {
        super(value, name);
    }

    public static MailOrientation forName(String name) {
        return forName(MailOrientation.class, name);
    }

    public static Collection<MailOrientation> values() {
        return values(MailOrientation.class);
    }

    public static MailOrientation forValue(Integer value) {
        return forValue(MailOrientation.class, value);
    }

    public static final MailOrientation FROM = new MailOrientation(1, "from");
    public static final MailOrientation RECIPIENT = new MailOrientation(2, "recipient");
    public static final MailOrientation CC = new MailOrientation(3, "cc");
    public static final MailOrientation BCC = new MailOrientation(4, "bcc");

}
