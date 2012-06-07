package com.bee32.plover.site.cfg;

import java.util.Collection;

public class OptimizationLevel
        extends SiteConfigEnum<Integer, OptimizationLevel>
        implements Comparable<OptimizationLevel> {

    private static final long serialVersionUID = 1L;

    public OptimizationLevel(int value, String name) {
        super(value, name);
    }

    @Override
    public int compareTo(OptimizationLevel o) {
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

    public static Collection<OptimizationLevel> values() {
        return values(OptimizationLevel.class);
    }

    public static OptimizationLevel forName(String altName) {
        return forName(OptimizationLevel.class, altName);
    }

    public static OptimizationLevel forValue(int value) {
        return forValue(OptimizationLevel.class, value);
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
