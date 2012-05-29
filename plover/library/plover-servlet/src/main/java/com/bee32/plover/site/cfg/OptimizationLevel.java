package com.bee32.plover.site.cfg;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.bee32.plover.arch.util.NoSuchEnumException;

public class OptimizationLevel
        extends SiteConfigEnum<Integer, OptimizationLevel> {

    private static final long serialVersionUID = 1L;

    public OptimizationLevel(int value, String name) {
        super(value, name);
    }

    static final Map<String, OptimizationLevel> nameMap = new HashMap<String, OptimizationLevel>();
    static final Map<Integer, OptimizationLevel> valueMap = new HashMap<Integer, OptimizationLevel>();

    public static Collection<OptimizationLevel> values() {
        Collection<OptimizationLevel> values = valueMap.values();
        return Collections.unmodifiableCollection(values);
    }

    public static OptimizationLevel forName(String altName) {
        OptimizationLevel level = nameMap.get(altName);
        if (level == null)
            throw new NoSuchEnumException(OptimizationLevel.class, altName);
        return level;
    }

    public static OptimizationLevel forValue(int value) {
        OptimizationLevel typeName = valueMap.get(value);
        if (typeName == null)
            throw new NoSuchEnumException(OptimizationLevel.class, value);

        return typeName;
    }

    /**
     * 调试模式: 无优化：低效、主要用于排错
     */
    public static final OptimizationLevel NONE = new OptimizationLevel(0, "none");

    /**
     * 调试模式: 最小优化：仅启用至关重要的优化
     */
    public static final OptimizationLevel LOW = new OptimizationLevel(1, "low");

    /**
     * 非调试模式: 中等优化：启用所有常规的优化
     */
    public static final OptimizationLevel MEDIUM = new OptimizationLevel(2, "medium");

    /**
     * 非调试模式: 高优化：高风险，可能导致潜在的数据不一致
     */
    public static final OptimizationLevel HIGH = new OptimizationLevel(3, "high");

}
