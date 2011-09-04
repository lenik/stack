package com.bee32.plover.test;

import java.util.logging.Logger;

import org.slf4j.LoggerFactory;

import com.bee32.plover.arch.Application;

public class TestLogging {

    static Logger jul = Logger.getLogger(TestLogging.class.getName());
    static org.slf4j.Logger slf4j = LoggerFactory.getLogger(TestLogging.class);

    public static void doJul() {
        jul.severe("Severe");
        jul.warning("Warning");
        jul.config("Config");
        jul.info("Info");
        jul.fine("- Fine");
        jul.finer("-- Finer");
        jul.finest("---- Finest");
    }

    public static void doSlf4j() {
        slf4j.info("Info message");
        slf4j.warn("Warn message");
        slf4j.error("Error message");
        slf4j.debug("--- Debug message");
        slf4j.trace("------- Trace message");
    }

    public static void main(String[] args) {
        Application.initialize();
        doJul();
        Application.terminate();
    }

}
