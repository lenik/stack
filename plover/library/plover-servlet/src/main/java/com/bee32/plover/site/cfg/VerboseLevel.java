package com.bee32.plover.site.cfg;

import com.bee32.plover.arch.util.ILabelledEntry;

public enum VerboseLevel
        implements ILabelledEntry {

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
    public String getEntryLabel() {
        return label;
    }

}
