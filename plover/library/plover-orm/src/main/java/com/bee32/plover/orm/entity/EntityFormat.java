package com.bee32.plover.orm.entity;

import java.util.HashMap;
import java.util.Map;

import javax.free.IllegalUsageError;

public class EntityFormat {

    public static final EntityFormat SIMPLE = new EntityFormat(true, false, false, false, 0);
    public static final EntityFormat SHORT = new EntityFormat(true, true, false, false, 0);
    public static final EntityFormat NORMAL = new EntityFormat(true, true, true, true, 0, SIMPLE);
    public static final EntityFormat VERBOSE = new EntityFormat(true, true, true, true, 1, SIMPLE);
    public static final EntityFormat LOG_ENTRY = new EntityFormat(true, true, true, false, 1, SIMPLE);
    public static final EntityFormat FULL = new EntityFormat(true, true, true, true, 1000);

    private final boolean includeIdentity;
    private final boolean includeFields;
    private final boolean includeFieldNames;
    private final boolean multiLine;
    private final int depth;
    private EntityFormat reducedFormat;

    private EntityFormat(boolean includeIdentity, boolean includeFields, boolean includeFieldNames, boolean multiLine,
            int depth) {
        this.includeIdentity = includeIdentity;
        this.includeFields = includeFields;
        this.includeFieldNames = includeFieldNames;
        this.multiLine = multiLine;
        this.depth = depth;
        this.reducedFormat = this;
    }

    private EntityFormat(boolean includeIdentity, boolean includeFields, boolean includeFieldNames, boolean multiLine,
            int depth, EntityFormat reducedFormat) {
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

    public EntityFormat reduce() {
        return reducedFormat;
    }

    public static final String DEFAULT_PROPERTY = "plover.entity.format";

    public static EntityFormat DEFAULT = NORMAL;

    static Map<String, EntityFormat> map;
    static {
        map = new HashMap<String, EntityFormat>();
        map.put("SIMPLE", SIMPLE);
        map.put("SHORT", SHORT);
        map.put("NORMAL", NORMAL);
        map.put("VERBOSE", VERBOSE);
        map.put("LOG_ENTRY", LOG_ENTRY);
        map.put("FULL", FULL);
    }

    public static EntityFormat valueOf(String formatName) {
        return map.get(formatName);
    }

    static {
        String formatProperty = System.getProperty(DEFAULT_PROPERTY);

        if (formatProperty != null) {
            EntityFormat format = valueOf(formatProperty);

            if (format == null)
                throw new IllegalUsageError("Bad entity format: " + formatProperty);

            DEFAULT = format;
        }
    }

}
