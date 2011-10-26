package com.bee32.plover.site;

public class SiteIndex
        extends SiteTemplate {

    final PageMap pageMap;

    public SiteIndex(PageMap pageMap) {
        if (pageMap == null)
            throw new NullPointerException("pageMap");
        this.pageMap = pageMap;
    }

}
