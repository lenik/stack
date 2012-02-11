package com.bee32.plover.servlet.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;

import com.bee32.plover.arch.util.ApplicationContextPartialContext;
import com.bee32.plover.arch.util.IPartialContext;
import com.bee32.plover.site.SiteInstance;
import com.bee32.plover.site.SiteManager;

public class HttpPartialContext
        extends ApplicationContextPartialContext
        implements IPartialContext {

    @Override
    public ApplicationContext getAppCtx() {
        return ThreadHttpContext.requireApplicationContext();
    }

    public static ServletContext getApplication() {
        return ThreadHttpContext.getApplication();
    }

    public static ServletContext getServletContext() {
        return ThreadHttpContext.getServletContext();
    }

    public static ServletContext getServletContextOpt() {
        return ThreadHttpContext.getServletContextOpt();
    }

    public HttpServletRequest getRequest() {
        return ThreadHttpContext.getRequest();
    }

    public HttpServletRequest getRequestOpt() {
        return ThreadHttpContext.getRequestOpt();
    }

    public static HttpSession getSession() {
        return ThreadHttpContext.getSession();
    }

    public static HttpSession getSessionOpt() {
        return ThreadHttpContext.getSessionOpt();
    }

    public SiteInstance getSite() {
        return ThreadHttpContext.getSiteInstance();
    }

    public SiteInstance getSite(String nameOrAlias) {
        return SiteManager.getInstance().getSite(nameOrAlias);
    }

    public static final HttpPartialContext INSTANCE = new HttpPartialContext();

}
