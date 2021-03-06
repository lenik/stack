package com.bee32.zebra.tk.htm;

import net.bodz.bas.t.variant.IVariantMap;
import net.bodz.bas.t.variant.MutableVariantMap;

public class PageLayout {

    public static final String ATTRIBUTE_KEY = PageLayout.class.getName();

    public final IVariantMap<String> attributeMap;

    /**
     * Hide the menu, head/foot, etc. in the picker-dialog.
     */
    private boolean showFrame = true;

    public PageLayout() {
        attributeMap = new MutableVariantMap<>();
    }

    public boolean isShowFrame() {
        return showFrame;
    }

    public void setShowFrame(boolean showFrame) {
        this.showFrame = showFrame;
    }

    public boolean isHideFrame() {
        return !showFrame;
    }

    public void setHideFrame(boolean hideFrame) {
        this.showFrame = !hideFrame;
    }

}
