package com.bee32.sem.mail.entity;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import com.bee32.plover.arch.util.EnumAlt;
import com.bee32.plover.arch.util.NoSuchEnumException;

public class MailOrientation
        extends EnumAlt<Integer, MailOrientation> {

    private static final long serialVersionUID = 1L;

    static final Map<String, MailOrientation> nameMap = getNameMap(MailOrientation.class);
    static final Map<Integer, MailOrientation> valueMap = getValueMap(MailOrientation.class);

    public MailOrientation(int value, String name) {
        super(value, name);
    }

    public static Collection<MailOrientation> values() {
        Collection<MailOrientation> values = valueMap.values();
        return Collections.unmodifiableCollection(values);
    }

    public static MailOrientation forName(String altName) {
        MailOrientation orientation = nameMap.get(altName);
        if (orientation == null)
            throw new NoSuchEnumException(MailOrientation.class, altName);
        return orientation;
    }

    public static MailOrientation valueOf(int value) {
        MailOrientation orientation = valueMap.get(value);
        if (orientation == null)
            throw new NoSuchEnumException(MailOrientation.class, value);

        return orientation;
    }

    public static final MailOrientation FROM = new MailOrientation(1, "from");
    public static final MailOrientation RECIPIENT = new MailOrientation(2, "recipient");
    public static final MailOrientation CC = new MailOrientation(3, "cc");
    public static final MailOrientation BCC = new MailOrientation(4, "bcc");

}
