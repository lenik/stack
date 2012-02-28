package com.bee32.plover.site;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.servlet.util.ServiceTemplateRC;

public abstract class AbstractSll
        extends ServiceTemplateRC
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
    public void destroySite(SiteInstance site) {
        logger.debug("Site destroyed: " + site);
    }

    @Override
    public void addSite(SiteInstance site) {
        logger.debug("Site inited: " + site);
    }

    @Override
    public void removeSite(SiteInstance site) {
        logger.debug("Site removed: " + site);
    }

    @Override
    public void startSite(SiteInstance site) {
        logger.debug("Site started: " + site);
    }

    @Override
    public void stopSite(SiteInstance site) {
        logger.debug("Site stopped: " + site);
    }

}
