package com.bee32.plover.site.cfg;


public enum SamplesSelection
        implements ILabel {

    NONE("无（全定制系统，不建议使用）"),

    STANDARD("标准（建议）"),

    NORMAL("常规测试样本（适用于集成测试）"),

    WORSE("边界样本（仅适用边界测试）"),

    ;

    private final String label;

    SamplesSelection(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }

}
