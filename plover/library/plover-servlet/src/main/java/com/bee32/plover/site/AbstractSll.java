package com.bee32.plover.site;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.inject.ServiceTemplate;

@ServiceTemplate
public abstract class AbstractSll
        implements ISiteLifecycleListener {

    static Logger logger = LoggerFactory.getLogger(AbstractSll.class);

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public void createSite(SiteInstance site) {
        logger.debug("Site created: " + site);
    }

    @Override
    public void loadSite(SiteInstance site) {
        logger.debug("Site inited: " + site);
    }

    @Override
    public void removeSite(SiteInstance site) {
        logger.debug("Site removed: " + site);
    }

    @Override
    public void destroySite(SiteInstance site) {
        logger.debug("Site destroyed: " + site);
    }

}
