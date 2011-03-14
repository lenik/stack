package com.bee32.plover.orm.entity;

public enum EntityFormat {

    SHORT, //

    VERBOSE, //

    LOG_ENTRY, //
    ;

    public static final String DEFAULT_PROPERTY = "plover.entity.format";

    public static EntityFormat DEFAULT = SHORT;

    static {
        String formatProperty = System.getProperty(DEFAULT_PROPERTY);

        if (formatProperty != null) {
            EntityFormat format = EntityFormat.valueOf(formatProperty);
            // if (format == null)
            // throw new IllegalUsageError("Bad entity format: " + formatProperty);
            DEFAULT = format;
        }
    }

}
