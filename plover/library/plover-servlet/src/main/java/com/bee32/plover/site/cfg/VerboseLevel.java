package com.bee32.plover.site.cfg;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.bee32.plover.arch.util.NoSuchEnumException;

public class VerboseLevel
        extends SiteConfigEnum<Integer, VerboseLevel> {

    private static final long serialVersionUID = 1L;

    public VerboseLevel(int value, String name) {
        super(value, name);
    }

    static final Map<String, VerboseLevel> nameMap = new HashMap<String, VerboseLevel>();
    static final Map<Integer, VerboseLevel> valueMap = new HashMap<Integer, VerboseLevel>();

    public static Collection<VerboseLevel> values() {
        Collection<VerboseLevel> values = valueMap.values();
        return Collections.unmodifiableCollection(values);
    }

    public static VerboseLevel forName(String altName) {
        VerboseLevel verboseLevel = nameMap.get(altName);
        if (verboseLevel == null)
            throw new NoSuchEnumException(VerboseLevel.class, altName);
        return verboseLevel;
    }

    public static VerboseLevel forValue(int value) {
        VerboseLevel verboseLevel = valueMap.get(value);
        if (verboseLevel == null)
            throw new NoSuchEnumException(VerboseLevel.class, value);

        return verboseLevel;
    }

    /** 安静 */
    public static final VerboseLevel QUIET = new VerboseLevel(0, "quiet");
    /** 仅SQL */
    public static final VerboseLevel SQL = new VerboseLevel(1, "sql");
    /** 调试模式 */
    public static final VerboseLevel DEBUG = new VerboseLevel(4, "debug");
    /** 跟踪模式 */
    public static final VerboseLevel TRACE = new VerboseLevel(5, "trace");

}
