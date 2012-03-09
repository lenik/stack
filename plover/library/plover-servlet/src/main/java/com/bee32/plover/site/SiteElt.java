package com.bee32.plover.site;

import java.util.Arrays;
import java.util.Collection;

import com.bee32.plover.arch.logging.AbstractElt;
import com.bee32.plover.arch.logging.ExceptionLog;
import com.bee32.plover.servlet.util.ThreadHttpContext;

public class SiteElt
        extends AbstractElt {

    public static final String LOG_KEY = "exception-log";
    public static final int DEFAULT_LOG_SIZE = 100;

    @Override
    public synchronized Collection<? extends ExceptionLog> getLogTargets() {
        SiteInstance site = ThreadHttpContext.getSiteInstance();
        ExceptionLog log = site.getAttribute(LOG_KEY);
        if (log == null) {
            log = new ExceptionLog(DEFAULT_LOG_SIZE);
            site.setAttribute(LOG_KEY, log);
        }
        return Arrays.asList(log);
    }

}
