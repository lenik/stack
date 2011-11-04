package com.bee32.plover.site.scope;

import java.util.Map;

import com.bee32.plover.inject.scope.AbstractScope;
import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.plover.site.LoadSiteException;
import com.bee32.plover.site.SiteInstance;
import com.bee32.plover.site.SiteManager;

public class SiteScope
        extends AbstractScope {

    SiteManager siteManager = SiteManager.getInstance();

    @Override
    protected Map<String, Object> getBeanMap() {
        SiteInstance site;
        try {
            site = ThreadHttpContext.getSiteInstance();
        } catch (LoadSiteException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        Map<String, Object> attributes = site.getAttributes();
        return attributes;
    }

    @Override
    public String getConversationId() {
        SiteInstance site;
        try {
            site = ThreadHttpContext.getSiteInstance();
        } catch (LoadSiteException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        String siteName = site.getName();
        return siteName;
    }

    @Override
    public Object resolveContextualObject(String key) {
        return super.resolveContextualObject(key);
    }

}
