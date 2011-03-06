package com.bee32.plover.arch.operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerResultOutput
        implements IResultOutput {

    static Logger logger = LoggerFactory.getLogger(LoggerResultOutput.class);

    @Override
    public void put(Object obj) {
        logger.info(String.valueOf(obj));
    }

    private static final LoggerResultOutput instance = new LoggerResultOutput();

    public static LoggerResultOutput getInstance() {
        return instance;
    }

}
