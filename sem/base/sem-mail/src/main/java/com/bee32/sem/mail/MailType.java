package com.bee32.sem.mail;

import java.util.Locale;
import java.util.ResourceBundle;

public enum MailType {

    __system__,

    BCAST(__system__),

    NOTICE(__system__),

    __issue__,

    ISSUE(__issue__),

    __converted__,

    EMAIL(__converted__),

    FAX(__converted__),

    __social__,

    USER(__social__),

    QUESTION(__social__),

    POST(__social__),

    COMMENT(__social__),

    ;

    private final MailType baseType;

    static ResourceBundle rb;
    static {
        rb = ResourceBundle.getBundle(MailType.class.getName());
    }

    MailType() {
        this(null);
    }

    MailType(MailType baseType) {
        this.baseType = baseType;
    }

    public MailType getBaseType() {
        return baseType;
    }

    public String getDisplayName() {
        return getDisplayName(null);
    }

    public String getDisplayName(Locale locale) {
        return rb.getString(name() + ".label");
    }

    public String getDescription() {
        return getDescription(null);
    }

    public String getDescription(Locale locale) {
        return rb.getString(name() + ".description");
    }

}
