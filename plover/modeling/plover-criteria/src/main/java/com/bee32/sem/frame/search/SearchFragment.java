package com.bee32.sem.frame.search;

import java.io.Serializable;

import com.bee32.plover.arch.util.ILabelledEntry;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.util.FormatStyle;
import com.bee32.plover.util.IMultiFormat;

public abstract class SearchFragment
        implements ILabelledEntry, Serializable {

    private static final long serialVersionUID = 1L;

    public abstract ICriteriaElement compose();

    protected static String toSimpleString(Object obj) {
        if (obj == null)
            return "(空)";

        if (obj instanceof ILabelledEntry) {
            ILabelledEntry entry = (ILabelledEntry) obj;
            return entry.getEntryLabel();
        }

        if (obj instanceof IMultiFormat) {
            IMultiFormat mf = (IMultiFormat) obj;
            mf.toString(FormatStyle.SIMPLE);
        }

        return String.valueOf(obj);
    }

    @Override
    public String toString() {
        return getEntryLabel();
    }

}
