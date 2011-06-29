package com.bee32.sem.calendar;

import java.io.IOException;

import com.bee32.sem.test.SEMTestCase;

public class SEMCalendarModuleTest
        extends SEMTestCase {

    @Override
    protected int getRefreshPeriod() {
        return 10;
    }

    @Override
    protected String getLoggedInUser() {
        return "eva";
    }

    @Override
    public void mainLoop()
            throws IOException {
        new SEMCalendarModuleTest().browseAndWait(//
                // SEMCalendar
                );
    }
}
