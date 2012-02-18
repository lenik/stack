package com.bee32.plover.site.scope;

import java.util.Date;

import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.servlet.PloverServletModule;
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
public class StartAndStatsSrl
        extends AbstractSrl {

    static Logger logger = LoggerFactory.getLogger(StartAndStatsSrl.class);

    public static final int PRIORITY = 10;

    public static final String REQUEST_BEGIN_ATTRIBUTE = "requestBegin";

    SiteManager siteManager = SiteManager.getInstance();

    @Override
    public int getPriority() {
        return PRIORITY;
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();

        // Skip plover-servlet ops.
        String contextPath = request.getSession().getServletContext().getContextPath();
        String _prefix = contextPath + PloverServletModule.PREFIX_;
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith(_prefix))
            return;

        request.setAttribute(REQUEST_BEGIN_ATTRIBUTE, new Date());

        SiteInstance site = ThreadHttpContext.getSiteInstance(request);
        site.start();
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        SiteInstance site = ThreadHttpContext.getSiteInstance(request);

        Date requestBegin = (Date) request.getAttribute(REQUEST_BEGIN_ATTRIBUTE);
        if (requestBegin != null) {
            long serviceTime = new Date().getTime() - requestBegin.getTime();

            // Skip plover-servlet ops.
            boolean bee32 = false;
            String contextPath = request.getSession().getServletContext().getContextPath();
            String requestURI = request.getRequestURI();
            if (requestURI.startsWith(contextPath + "/3"))
                bee32 = true;

            String facesRequest = request.getHeader("Faces-Request");
            boolean micro = "partial/ajax".equals(facesRequest);

            site.getLocalStats().addService(serviceTime, bee32, micro);

            if (bee32 && !micro)
                site.addRecentRequest(request);
        }
    }

}
