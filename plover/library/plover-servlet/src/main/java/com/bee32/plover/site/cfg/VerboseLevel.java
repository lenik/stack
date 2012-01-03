package com.bee32.plover.site.cfg;


public enum VerboseLevel
        implements ILabel {

    QUIET("安静"),

    SQL("仅SQL"),

    DEBUG("调试模式"),

    TRACE("跟踪模式"),

    ;

    final String label;

    private VerboseLevel(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }

}
