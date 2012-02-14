package com.bee32.plover.site.scope;

import javax.servlet.ServletRequestEvent;

import com.bee32.plover.servlet.peripheral.AbstractSrl;
import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.plover.servlet.util.ThreadServletRequestListener;
import com.bee32.plover.site.SiteInstance;
import com.bee32.plover.site.SiteManager;

/**
 * IMPORTANT:
 * <ul>
 * <li>This listener must be fired after {@link ThreadServletRequestListener}.
 * <li>For standalone (scope-wired) test, it should start the default site by hand.
 * </ul>
 */
public class SiteStartStopListener
        extends AbstractSrl {

    public static final int PRIORITY = 10;

    SiteManager siteManager = SiteManager.getInstance();

    @Override
    public int getPriority() {
        return PRIORITY;
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        SiteInstance site = ThreadHttpContext.getSiteInstance();
        site.start();
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
    }

}
