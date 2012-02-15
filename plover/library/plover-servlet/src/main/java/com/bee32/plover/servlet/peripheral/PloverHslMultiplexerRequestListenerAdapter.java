package com.bee32.plover.servlet.peripheral;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

import com.bee32.plover.inject.NotAComponent;

/**
 * An SRL-adapter for {@link PloverHslMultiplexer}.
 *
 * http-request -> SRL multiplexer -> (this) -> HSL multiplexer -> HSL*.
 *
 * @see PloverHslMultiplexer
 * @see PloverSrlMultiplexer
 */
/* Add this is the servlet container supports http session listener */@NotAComponent
public class PloverHslMultiplexerRequestListenerAdapter
        implements ServletRequestListener {

    public static final String CREATED_KEY = "plover-hsl-multiplexer-created";

    PloverHslMultiplexer hslMultiplexer = new PloverHslMultiplexer();

    @Override
    public synchronized void requestInitialized(ServletRequestEvent sre) {
        ServletRequest request = sre.getServletRequest();
        if (!(request instanceof HttpServletRequest))
            return;
        HttpServletRequest req = (HttpServletRequest) request;

        HttpSession session = req.getSession();

        Object created = session.getAttribute(CREATED_KEY);
        if (created == null) {
            session.setAttribute(CREATED_KEY, true);
            HttpSessionEvent hse = new HttpSessionEvent(session);
            hslMultiplexer.sessionCreated(hse);
        }
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
    }

}
