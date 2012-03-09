package com.bee32.plover.servlet.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bee32.plover.inject.GlobalAppCtx;
import com.bee32.plover.site.NoSuchSiteException;
import com.bee32.plover.site.SiteException;
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
        WebApplicationContext wappctx = getWebApplicationContext();
        if (wappctx != null)
            return wappctx;
        ApplicationContext gappctx = GlobalAppCtx.getApplicationContext();
        if (gappctx != null)
            return gappctx;
        throw new IllegalStateException("Neither web-appctx nor global-appctx is existed.");
    }

    public static void autowireThisServlet(HttpServlet thisServlet) {
        AutowireCapableBeanFactory beanFactory = requireApplicationContext().getAutowireCapableBeanFactory();
        beanFactory.autowireBean(thisServlet);
    }

    public static boolean allowNullRequest = true;
    public static boolean autoCreateMode = false;

    public static SiteInstance getSiteInstance() {
        HttpServletRequest request = allowNullRequest ? getRequestOpt() : getRequest();
        return getSiteInstance(request, true);
    }

    public static SiteInstance getSiteInstanceOpt() {
        HttpServletRequest request = allowNullRequest ? getRequestOpt() : getRequest();
        return getSiteInstance(request, false);
    }

    public static SiteInstance getSiteInstance(HttpServletRequest request, boolean required)
            throws SiteException {
        SiteManager siteManager = SiteManager.getInstance();

        String siteAlias = SiteNaming.getDefaultSiteName();

        if (request != null)
            siteAlias = SiteNaming.getSiteAlias(request);

        SiteInstance siteInstance;
        if (autoCreateMode) {
            siteInstance = siteManager.getOrLoadSite(siteAlias);
        } else {
            siteInstance = siteManager.getSite(siteAlias);
        }

        if (siteInstance == null && required)
            throw new NoSuchSiteException(siteAlias);

        return siteInstance;
    }
}
