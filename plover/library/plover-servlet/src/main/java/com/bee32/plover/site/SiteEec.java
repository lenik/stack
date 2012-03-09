package com.bee32.plover.site;

import javax.servlet.http.HttpServletRequest;

import com.bee32.plover.arch.logging.AbstractEec;
import com.bee32.plover.arch.logging.ExceptionLogEntry;
import com.bee32.plover.servlet.util.ThreadHttpContext;

public class SiteEec
        extends AbstractEec {

    public static final String REQUEST_URI_KEY = "requestURI";

    public SiteEec() {
        ExceptionLogEntry.registerAttribute(REQUEST_URI_KEY, "请求地址");
    }

    @Override
    public void completeEntry(ExceptionLogEntry entry) {
        HttpServletRequest request = ThreadHttpContext.getRequestOpt();
        if (request != null)
            entry.setAttribute(REQUEST_URI_KEY, request.getRequestURI());

        SiteInstance site = ThreadHttpContext.getSiteInstanceOpt();
        if (site != null)
            site.getLocalStats().addException(entry);
    }

}
