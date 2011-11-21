package com.bee32.plover.servlet.peripheral;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ServiceLoader;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.arch.util.PriorityComparator;

public class PloverSrlMultiplexer
        implements ServletRequestListener {

    static Logger logger = LoggerFactory.getLogger(PloverSrlMultiplexer.class);

    static List<IServletRequestListener> srlv;
    static {
        srlv = new ArrayList<IServletRequestListener>();
        for (IServletRequestListener srl : ServiceLoader.load(IServletRequestListener.class)) {
            srlv.add(srl);
        }
        Collections.sort(srlv, PriorityComparator.INSTANCE);
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        for (IServletRequestListener srl : srlv) {
            logger.debug("SRL-init: " + srl.getClass().getName());
            try {
                srl.requestInitialized(sre);
            } catch (Exception e) {
                logger.error("SRL-Init error: " + srl.getClass().getName(), e);
            }
        }
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        for (int i = srlv.size() - 1; i >= 0; i--) {
            IServletRequestListener srl = srlv.get(i);
            logger.debug("SRL-Terminate: " + srl.getClass().getName());
            try {
                srl.requestDestroyed(sre);
            } catch (Exception e) {
                logger.error("SRL-Terminate error: " + srl.getClass().getName(), e);
            }
        }
    }

}
