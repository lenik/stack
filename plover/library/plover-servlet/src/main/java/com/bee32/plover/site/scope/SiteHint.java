package com.bee32.plover.site.scope;

import org.springframework.stereotype.Component;

import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.plover.site.LoadSiteException;
import com.bee32.plover.site.SiteInstance;
import com.bee32.plover.site.SiteManager;

@Component
@PerSite
public class SiteHint {

    static final SiteManager manager = SiteManager.getInstance();

    final SiteInstance site;
    String hint;

    public SiteHint()
            throws LoadSiteException {
        site = ThreadHttpContext.getSiteInstance();
    }

    public SiteManager getManager() {
        return manager;
    }

    public SiteInstance getSite() {
        return site;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

}
