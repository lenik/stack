package com.bee32.plover.site;

import com.bee32.plover.arch.util.IOrdered;

public interface ISiteLifecycleListener
        extends IOrdered {

    void createSite(SiteInstance site);

    void loadSite(SiteInstance site);

    void removeSite(SiteInstance site);

    void destroySite(SiteInstance site);

}
