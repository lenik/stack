package com.bee32.sem.test;

import java.io.IOException;

import com.bee32.plover.orm.config.test.TestDataSource;

public class SEMUnitServer
        extends SEMTestCase {

    public SEMUnitServer() {
        String name = getDataSource();
        TestDataSource.NAME = name;
    }

    protected String getDataSource() {
        return "test";
    }

    @Override
    protected int getPort() {
        return 10000;
    }

    @Override
    protected boolean isDebugMode() {
        return false;
    }

    @Override
    protected int getRefreshPeriod() {
        return 864000;
    }

    @Override
    protected String getLoggedInUser() {
        return null;
    }

    public void startAndWait(String... args)
            throws IOException {
        unit().mainLoop();
    }

}
