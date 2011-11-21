package com.bee32.plover.servlet.peripheral;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ServiceLoader;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.arch.util.PriorityComparator;

/**
 * You can add this multiplexer to the servlet-container, if it supports http session listener.
 *
 * Otherwise, you need
 */
public class PloverHslMultiplexer
        implements HttpSessionListener {

    static Logger logger = LoggerFactory.getLogger(PloverSrlMultiplexer.class);

    static List<IHttpSessionListener> hslv;
    static {
        hslv = new ArrayList<IHttpSessionListener>();
        for (IHttpSessionListener hsl : ServiceLoader.load(IHttpSessionListener.class)) {
            hslv.add(hsl);
        }
        Collections.sort(hslv, PriorityComparator.INSTANCE);
    }

    @Override
    public void sessionCreated(HttpSessionEvent hse) {
        for (IHttpSessionListener hsl : hslv) {
            logger.debug("HSL-init: " + hsl.getClass().getName());
            try {
                hsl.sessionCreated(hse);
            } catch (Exception e) {
                logger.error("HSL-Init error: " + hsl.getClass().getName(), e);
            }
        }
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent hse) {
        for (int i = hslv.size() - 1; i >= 0; i--) {
            IHttpSessionListener hsl = hslv.get(i);
            logger.debug("HSL-Terminate: " + hsl.getClass().getName());
            try {
                hsl.sessionDestroyed(hse);
            } catch (Exception e) {
                logger.error("HSL-Terminate error: " + hsl.getClass().getName(), e);
            }
        }
    }

}
