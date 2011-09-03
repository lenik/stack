package com.bee32.plove1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLogging {

    static Logger logger;
    static {
        logger = LoggerFactory.getLogger(TestLogging.class);
    }

    public static void main(String[] args) {
        System.out.println("BEGIN");
        logger.info("Info message");
        logger.warn("Warn message");
        logger.error("Error message");
        logger.debug("--- Debug message 1");
        logger.debug("--- Debug message 2");
        logger.debug("--- Debug message 3");
        System.out.println("END");
    }

}
