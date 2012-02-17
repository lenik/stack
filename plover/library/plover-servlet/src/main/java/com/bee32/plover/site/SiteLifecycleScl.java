package com.bee32.plover.site;

import javax.servlet.ServletContextEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.servlet.peripheral.AbstractScl;

public class SiteLifecycleScl
        extends AbstractScl {

    static Logger logger = LoggerFactory.getLogger(SiteLifecycleScl.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // site should be lazy-started.
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        SiteManager siteManager = SiteManager.getInstance();
        for (SiteInstance site : siteManager.getSites()) {
            try {
                site.stop();
            } catch (Exception e) {
                logger.error("Failed to stop site: " + site.getName(), e);
            }
        }
    }

}
