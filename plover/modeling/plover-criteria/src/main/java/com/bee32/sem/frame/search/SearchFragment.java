package com.bee32.sem.frame.search;

import java.io.Serializable;

import javax.free.IllegalUsageException;

import com.bee32.plover.arch.util.ILabelledEntry;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.util.FormatStyle;
import com.bee32.plover.util.IMultiFormat;

public abstract class SearchFragment
        extends CriteriaSpec
        implements ILabelledEntry, Serializable {

    private static final long serialVersionUID = 1L;

    private ISearchFragmentsHolder holder;

    public abstract ICriteriaElement compose();

    public ISearchFragmentsHolder getHolder() {
        return holder;
    }

    public void setHolder(ISearchFragmentsHolder holder) {
        this.holder = holder;
    }

    public void detach() {
        if (holder == null)
            throw new IllegalUsageException("The fragment holder isn't set");
        holder.removeSearchFragment(this);
    }

    @Override
    public String toString() {
        return getEntryLabel();
    }

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

}
