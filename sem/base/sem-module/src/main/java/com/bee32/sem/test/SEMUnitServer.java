package com.bee32.sem.test;

import java.io.IOException;

public class SEMUnitServer
        extends SEMTestCase {

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
