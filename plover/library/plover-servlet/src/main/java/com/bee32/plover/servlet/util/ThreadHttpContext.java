package com.bee32.plover.servlet.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bee32.plover.site.LoadSiteException;
import com.bee32.plover.site.SiteInstance;
import com.bee32.plover.site.SiteManager;
import com.bee32.plover.site.scope.SiteNaming;

public class ThreadHttpContext
        extends ThreadServletContext {

    public static WebApplicationContext getWebApplicationContext() {
        ServletContext sc = getServletContextOpt();
        if (sc == null)
            return null;
        return WebApplicationContextUtils.getWebApplicationContext(sc);
    }

    public static ApplicationContext getApplicationContext() {
        return getWebApplicationContext();
    }

    public static WebApplicationContext requireWebApplicationContext() {
        return WebApplicationContextUtils.getWebApplicationContext(getServletContext());
    }

    public static ApplicationContext requireApplicationContext() {
        return requireWebApplicationContext();
    }

    public static void autowireThisServlet(HttpServlet thisServlet) {
        AutowireCapableBeanFactory beanFactory = requireApplicationContext().getAutowireCapableBeanFactory();
        beanFactory.autowireBean(thisServlet);
    }

    static boolean allowNullRequest = true;
    static boolean autoCreateMode = false;

    public static SiteInstance getSiteInstance()
            throws LoadSiteException {
        SiteManager siteManager = SiteManager.getInstance();

        String siteAlias = SiteNaming.DEFAULT;

        HttpServletRequest request = allowNullRequest ? getRequestOpt() : getRequest();
        if (request != null)
            siteAlias = SiteNaming.getSiteAlias(request);

        SiteInstance siteInstance;
        if (autoCreateMode)
            siteInstance = siteManager.getOrCreateSite(siteAlias);
        else
            siteInstance = siteManager.getSite(siteAlias);

        return siteInstance;
    }

}
