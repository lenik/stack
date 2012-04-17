package com.bee32.sem.module;

import java.io.IOException;

import com.bee32.sem.test.SEMTestCase;

public class SEMModuleServer
        extends SEMTestCase {

    public SEMModuleServer() {
        contextPath = "";
    }

    @Override
    protected int getPort() {
        return 10001;
    }

    @Override
    protected boolean isDebugMode() {
        return false;
    }

    @Override
    protected int getRefreshPeriod() {
        return 60; // 1 minute
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
