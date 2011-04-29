package com.bee32.plover.util;

import java.util.HashMap;
import java.util.Map;

import javax.free.IllegalUsageError;

public class FormatStyle {

    static final boolean IDENTITY = true;
    static final boolean FIELDS = true;
    static final boolean FIELD_NAMES = true;
    static final boolean MULTI_LINE = true;

    public static final FormatStyle SIMPLE //
    /*    */= new FormatStyle(IDENTITY, false, false, false, 0);

    public static final FormatStyle SHORT //
    /*    */= new FormatStyle(IDENTITY, FIELDS, false, false, 0);

    public static final FormatStyle NORMAL //
    /*    */= new FormatStyle(IDENTITY, FIELDS, FIELD_NAMES, MULTI_LINE, 0, SIMPLE);

    public static final FormatStyle VERBOSE //
    /*    */= new FormatStyle(IDENTITY, FIELDS, FIELD_NAMES, MULTI_LINE, 1, SIMPLE);

    public static final FormatStyle LOG_ENTRY //
    /*    */= new FormatStyle(IDENTITY, FIELDS, FIELD_NAMES, false, 1, SIMPLE);

    public static final FormatStyle FULL //
    /*    */= new FormatStyle(IDENTITY, FIELDS, FIELD_NAMES, MULTI_LINE, 1000);

    private final boolean includeIdentity;
    private final boolean includeFields;
    private final boolean includeFieldNames;
    private final boolean multiLine;
    private final int depth;
    private FormatStyle reducedFormat;

    private FormatStyle(boolean includeIdentity, boolean includeFields, boolean includeFieldNames, boolean multiLine,
            int depth) {
        this.includeIdentity = includeIdentity;
        this.includeFields = includeFields;
        this.includeFieldNames = includeFieldNames;
        this.multiLine = multiLine;
        this.depth = depth;
        this.reducedFormat = this;
    }

    private FormatStyle(boolean includeIdentity, boolean includeFields, boolean includeFieldNames, boolean multiLine,
            int depth, FormatStyle reducedFormat) {
        this(includeIdentity, includeFields, includeFieldNames, multiLine, depth);
        this.reducedFormat = reducedFormat;
    }

    public boolean isIdentityIncluded() {
        return includeIdentity;
    }

    public boolean isFieldsIncluded() {
        return includeFields;
    }

    public boolean isNamesIncluded() {
        return includeFieldNames;
    }

    public boolean isMultiLine() {
        return multiLine;
    }

    public int getDepth() {
        return depth;
    }

    public FormatStyle reduce() {
        return reducedFormat;
    }

    public static final String DEFAULT_PROPERTY = "plover.entity.format";

    public static FormatStyle DEFAULT = NORMAL;

    static Map<String, FormatStyle> map;
    static {
        map = new HashMap<String, FormatStyle>();
        map.put("SIMPLE", SIMPLE);
        map.put("SHORT", SHORT);
        map.put("NORMAL", NORMAL);
        map.put("VERBOSE", VERBOSE);
        map.put("LOG_ENTRY", LOG_ENTRY);
        map.put("FULL", FULL);
    }

    public static FormatStyle valueOf(String formatName) {
        return map.get(formatName);
    }

    static {
        String formatProperty = System.getProperty(DEFAULT_PROPERTY);

        if (formatProperty != null) {
            FormatStyle format = valueOf(formatProperty);

            if (format == null)
                throw new IllegalUsageError("Bad entity format: " + formatProperty);

            DEFAULT = format;
        }
    }

}
