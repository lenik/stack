package com.bee32.zebra.tk.htm;

import net.bodz.bas.t.variant.IVariantMap;
import net.bodz.bas.t.variant.MutableVariantMap;

public class PageLayoutGuide {

    public final IVariantMap<String> attributeMap;
    public boolean hideFramework;

    public PageLayoutGuide() {
        attributeMap = new MutableVariantMap<>();
    }

}
