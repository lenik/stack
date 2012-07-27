package com.bee32.sem.module;

import javax.free.IllegalUsageException;

import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.plover.site.SiteInstance;
import com.bee32.plover.site.cfg.SitePropertyPrefix;
import com.bee32.plover.site.scope.PerSite;

/**
 * You should annotate with @SiteConfigGroup in concrete implementations.
 */
@PerSite
public abstract class AbstractModuleTerms {

    String propertyPrefix;

    public AbstractModuleTerms() {
        SitePropertyPrefix _sitePropertyPrefix = getClass().getAnnotation(SitePropertyPrefix.class);
        if (_sitePropertyPrefix == null)
            throw new IllegalUsageException("You didn't annotate the " + getClass() + " with annotation "
                    + SitePropertyPrefix.class.getName());
        String prefix = _sitePropertyPrefix.value();
        if (!prefix.endsWith("."))
            prefix += ".";
        this.propertyPrefix = prefix;
    }

    protected SiteInstance getSite() {
        SiteInstance site = ThreadHttpContext.getSiteInstance();
        return site;
    }

    protected String getProperty(String name) {
        String propertyName = propertyPrefix + name;
        return getSite().getProperty(propertyName);
    }

    protected void setProperty(String name, String value) {
        String propertyName = propertyPrefix + name;
        getSite().setProperty(propertyName, value);
    }

}
