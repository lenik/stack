package com.bee32.plover.arch.logging;

import java.util.logging.Handler;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.slf4j.bridge.SLF4JBridgeHandler;

import com.bee32.plover.arch.ApplicationLifecycle;

public class JulAlc
        extends ApplicationLifecycle {

    @Override
    public void initialize() {
        bridgeJulToSlf4j();
    }

    /**
     * Reset JUL and bridge to SLF4J.
     */
    void bridgeJulToSlf4j() {
        // Remove all existing handlers.
        Logger logger = LogManager.getLogManager().getLogger("");
        for (Handler h : logger.getHandlers())
            logger.removeHandler(h);

        // Install the bridge.
        SLF4JBridgeHandler.install();
    }

}
