package com.bee32.plover.site.scope;

import org.springframework.stereotype.Component;

import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.plover.site.LoadSiteException;
import com.bee32.plover.site.SiteInstance;

@Component
@PerSite
public class SiteBean {

    final SiteInstance site;

    public SiteBean()
            throws LoadSiteException {
        site = ThreadHttpContext.getSiteInstance();
    }

    public SiteInstance getSite() {
        return site;
    }

}
