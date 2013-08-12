package com.bee32.plover.site.cfg;

import java.util.Collection;

public class VerboseLevel
        extends SiteConfigEnum<Integer, VerboseLevel>
        implements Comparable<VerboseLevel> {

    private static final long serialVersionUID = 1L;

    public VerboseLevel(int value, String name) {
        super(value, name);
    }

    @Override
    public int compareTo(VerboseLevel o) {
        if (o == null)
            return 1;
        if (this == o)
            return 0;
        int val1 = getValue();
        int val2 = o.getValue();
        int cmp = val1 - val2;
        if (cmp != 0)
            return cmp;
        else
            return -1; // undefined: cuz this != o.
    }

    public static Collection<VerboseLevel> values() {
        return values(VerboseLevel.class);
    }

    public static VerboseLevel forName(String altName) {
        return forName(VerboseLevel.class, altName);
    }

    public static VerboseLevel forValue(int value) {
        return forValue(VerboseLevel.class, value);
    }

    /**
     * 安静
     *
     * <p * lang="en">
     * Quiet
     */
    public static final VerboseLevel QUIET = new VerboseLevel(0, "quiet");

    /**
     * 仅SQL
     *
     * <p lang="en">
     * SQL-Only
     */
    public static final VerboseLevel SQL = new VerboseLevel(1, "sql");

    /**
     * 调试模式
     *
     * <p lang="en">
     * Debug Mode
     */
    public static final VerboseLevel DEBUG = new VerboseLevel(4, "debug");

    /**
     * 跟踪模式
     *
     * <p lang="en">
     * Trace Mode
     */
    public static final VerboseLevel TRACE = new VerboseLevel(5, "trace");

}
