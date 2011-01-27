package com.bee32.plover.arch.util;

import java.util.ArrayList;
import java.util.List;

import com.bee32.plover.arch.ui.IAppearance;

public abstract class LoadFlags {

    private List<IAppearance> appearances;

    public IAppearance getAppearance(int index) {
        if (appearances != null && index < appearances.size())
            return appearances.get(index);
        return null;
    }

    public void setAppearance(int index, IAppearance appearance) {
        if (appearances == null)
            appearances = new ArrayList<IAppearance>();
        appearances.set(index, appearance);
    }

    public abstract String getBitsString();

    @Override
    public String toString() {
        return getBitsString();
    }

}
