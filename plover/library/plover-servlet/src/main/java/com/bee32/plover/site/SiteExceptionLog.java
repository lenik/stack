package com.bee32.plover.site;

import com.bee32.plover.arch.logging.ExceptionLog;
import com.bee32.plover.arch.logging.ExceptionLogEntry;
import com.bee32.plover.servlet.util.ThreadHttpContext;

public class SiteExceptionLog
        extends ExceptionLog {

    public SiteExceptionLog(int size) {
        super(size);
    }

    @Override
    public void addException(ExceptionLogEntry entry) {
        super.addException(entry);
        SiteInstance site = ThreadHttpContext.getSiteInstance();
        if (site != null)
            site.getLocalStats().addException(entry);
    }

}
