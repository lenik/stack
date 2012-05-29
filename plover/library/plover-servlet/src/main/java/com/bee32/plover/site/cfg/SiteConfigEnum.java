package com.bee32.plover.site.cfg;

import java.io.Serializable;

import com.bee32.plover.arch.util.EnumAlt;
import com.bee32.plover.arch.util.ILabelledEntry;

public abstract class SiteConfigEnum<V extends Serializable, self_t extends SiteConfigEnum<V, self_t>>
        extends EnumAlt<V, self_t>
        implements ILabelledEntry {

    private static final long serialVersionUID = 1L;

    public SiteConfigEnum(V value, String name) {
        super(value, name);
    }

    @Override
    public String getEntryLabel() {
        return getLabel();
    }

}
