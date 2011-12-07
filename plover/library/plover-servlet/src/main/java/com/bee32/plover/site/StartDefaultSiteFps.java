package com.bee32.plover.site;

import org.springframework.context.ApplicationContext;

import com.bee32.plover.test.AbstractFps;

public class StartDefaultSiteFps
        extends AbstractFps {

    @Override
    public int getOrder() {
        return 1000;
    }

    @Override
    public void init(ApplicationContext appctx) {
        SiteInstance defaultSite = SiteManager.getInstance().getSite("default");
        SiteLifecycleDispatcher.startSite(defaultSite);
    }

}
