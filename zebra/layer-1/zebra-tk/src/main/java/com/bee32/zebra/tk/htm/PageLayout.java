package com.bee32.zebra.tk.htm;

import net.bodz.bas.t.variant.IVariantMap;
import net.bodz.bas.t.variant.MutableVariantMap;

public class PageLayout {

    public static final String ATTRIBUTE_KEY = PageLayout.class.getName();

    public final IVariantMap<String> attributeMap;
    public boolean hideFramework;

    public PageLayout() {
        attributeMap = new MutableVariantMap<>();
    }

}
