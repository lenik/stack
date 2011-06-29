package com.bee32.sem.module;

import java.io.IOException;

import com.bee32.plover.orm.config.test.TestDataSource;
import com.bee32.sem.test.SEMTestCase;

public class SEMModuleServer
        extends SEMTestCase {

    public SEMModuleServer() {
        String name = getDataSource();
        TestDataSource.NAME = name;
        contextPath = "";
    }

    protected String getDataSource() {
        return "main";
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
        return 3600;
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
