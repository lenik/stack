package com.bee32.sem.mail;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import com.bee32.plover.arch.util.EnumAlt;
import com.bee32.plover.arch.util.NoSuchEnumException;

public class MailPriority
        extends EnumAlt<Integer, MailPriority> {

    private static final long serialVersionUID = 1L;

    static final Map<String, MailPriority> nameMap = getNameMap(MailPriority.class);
    static final Map<Integer, MailPriority> valueMap = getValueMap(MailPriority.class);

    public MailPriority(int priority, String name) {
        super(priority, name);
    }

    public static Collection<MailPriority> values() {
        Collection<MailPriority> values = valueMap.values();
        return Collections.unmodifiableCollection(values);
    }

    public static MailPriority forName(String altName) {
        MailPriority mailPriority = nameMap.get(altName);
        if (mailPriority == null)
            throw new NoSuchEnumException(MailPriority.class, altName);
        return mailPriority;
    }

    public static MailPriority valueOf(int value) {
        MailPriority mailPriority = valueMap.get(value);
        if (mailPriority == null)
            throw new NoSuchEnumException(MailPriority.class, value);

        return mailPriority;
    }

    public static final MailPriority URGENT = new MailPriority(10, "urgent");
    public static final MailPriority NORMAL = new MailPriority(20, "normal");
    public static final MailPriority HIGH = new MailPriority(30, "high");
    public static final MailPriority LOW = new MailPriority(40, "low");

}