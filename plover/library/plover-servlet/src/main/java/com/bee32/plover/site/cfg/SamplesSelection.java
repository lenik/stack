package com.bee32.plover.site.cfg;

import java.util.Collection;

public class SamplesSelection
        extends SiteConfigEnum<Integer, SamplesSelection> {

    private static final long serialVersionUID = 1L;

    public SamplesSelection(int value, String name) {
        super(value, name);
    }

    public static Collection<SamplesSelection> values() {
        return values(SamplesSelection.class);
    }

    public static SamplesSelection forName(String altName) {
        return forName(SamplesSelection.class, altName);
    }

    public static SamplesSelection forValue(int value) {
        return forValue(SamplesSelection.class, value);
    }

    public static final int V_NONE = 0;
    public static final int V_STANDARD = 1;
    public static final int V_NORMAL = 2;
    public static final int V_BOUNDARIES = 3;

    /** 无（全定制系统，不建议使用） */
    public static final SamplesSelection NONE = new SamplesSelection(V_NONE, "none");
    /** 标准（建议） */
    public static final SamplesSelection STANDARD = new SamplesSelection(V_STANDARD, "standard");
    /** 常规测试样本（适用于集成测试） */
    public static final SamplesSelection NORMAL = new SamplesSelection(V_NORMAL, "normal");
    /** 边界样本（仅适用边界测试） */
    public static final SamplesSelection BOUNDARIES = new SamplesSelection(V_BOUNDARIES, "boundaries");

}
