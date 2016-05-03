package com.bee32.zebra.tk.repr;

import net.bodz.lily.model.base.CoObjectIndex;

import com.bee32.zebra.tk.htm.IPageLayoutGuider;
import com.bee32.zebra.tk.htm.PageLayout;

public abstract class QuickIndex
        extends CoObjectIndex
        implements IPageLayoutGuider {

    public QuickIndexFormat format = QuickIndexFormat.HTML;
    public boolean defaultPage = true;
    public PageLayout pageLayoutGuide = new PageLayout();

    @Override
    public void configure(PageLayout pageLayout) {
        switch (format) {
        case PICKER:
            pageLayout.setShowFrame(false);
            break;

        default:
        }
    }

}
