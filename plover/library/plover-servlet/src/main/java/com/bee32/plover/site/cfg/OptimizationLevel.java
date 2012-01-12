package com.bee32.plover.site.cfg;

import com.bee32.plover.arch.util.ILabelledEntry;

public enum OptimizationLevel
        implements ILabelledEntry {

    // 调试模式
    NONE("无优化：低效、主要用于排错"),

    // 调试模式
    LOW("最小优化：仅启用至关重要的优化"),

    // 非调试模式
    MEDIUM("中等优化：启用所有常规的优化"),

    // 非调试模式
    HIGH("高优化：高风险，可能导致潜在的数据不一致"),

    ;

    final String label;

    private OptimizationLevel(String label) {
        this.label = label;
    }

    @Override
    public String getEntryLabel() {
        return label;
    }

}
