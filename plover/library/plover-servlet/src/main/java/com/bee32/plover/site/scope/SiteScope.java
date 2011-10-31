package com.bee32.plover.site.scope;

import org.springframework.beans.factory.ObjectFactory;

import com.bee32.plover.inject.scope.AbstractScope;
import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.plover.site.LoadSiteException;
import com.bee32.plover.site.SiteInstance;
import com.bee32.plover.site.SiteManager;

public class SiteScope
        extends AbstractScope {

    SiteManager siteManager = SiteManager.getInstance();

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        SiteInstance site;
        try {
            site = ThreadHttpContext.getSiteInstance();
        } catch (LoadSiteException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

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
        SiteInstance site;
        try {
            site = ThreadHttpContext.getSiteInstance();
        } catch (LoadSiteException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        Object obj = site.removeAttribute(name);
        return obj;
    }

}
