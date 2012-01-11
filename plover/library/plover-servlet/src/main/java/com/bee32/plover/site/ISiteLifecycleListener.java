package com.bee32.plover.site;

import com.bee32.plover.arch.util.IOrdered;

public interface ISiteLifecycleListener
        extends IOrdered {

    void createSite(SiteInstance site);

    void destroySite(SiteInstance site);

    void addSite(SiteInstance site);

    void removeSite(SiteInstance site);

    void startSite(SiteInstance site);

    void stopSite(SiteInstance site);

}
