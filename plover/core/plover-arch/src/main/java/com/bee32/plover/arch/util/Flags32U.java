package com.bee32.plover.arch.util;

import java.util.ArrayList;
import java.util.List;

import com.bee32.plover.arch.ui.IAppearance;

public class Flags32U
        extends Flags32 {

    private static final long serialVersionUID = 1L;

    private List<IAppearance> appearances;

    public IAppearance getAppearance(int index) {
        if (appearances != null && index < appearances.size())
            return appearances.get(index);
        return null;
    }

    public void setAppearance(int index, IAppearance appearance) {
        if (appearances == null) {
            synchronized (this) {
                if (appearances == null) {
                    appearances = new ArrayList<IAppearance>();
                }
            }
        }
        appearances.set(index, appearance);
    }

}
