package com.bee32.plover.site.scope;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.ObjectFactory;

import com.bee32.plover.inject.scope.AbstractScope;
import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.plover.site.LoadSiteException;
import com.bee32.plover.site.SiteInstance;
import com.bee32.plover.site.SiteManager;

public class SiteScope
        extends AbstractScope {

    SiteManager siteManager = SiteManager.getInstance();

    SiteInstance getSiteForRequest() {
        HttpServletRequest request = ThreadHttpContext.getRequest();
        String alias = SiteNaming.getSiteAlias(request);
        SiteInstance site;
        try {
            site = siteManager.getOrCreateSite(alias);
        } catch (LoadSiteException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return site;
    }

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        SiteInstance site = getSiteForRequest();
        Object obj;
        if (site.containsAttribute(name)) {
            obj = site.getAttribute(name);
        } else {
            obj = objectFactory.getObject();
            site.setAttribute(name, obj);
        }
        return obj;
    }

    @Override
    public Object remove(String name) {
        SiteInstance site = getSiteForRequest();
        Object obj = site.removeAttribute(name);
        return obj;
    }

}
