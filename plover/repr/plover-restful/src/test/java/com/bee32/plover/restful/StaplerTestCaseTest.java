package com.bee32.plover.restful;

import org.junit.Test;
import org.mortbay.jetty.testing.HttpTester;

public class StaplerTestCaseTest
        extends StaplerTestCase {

    BookStore store = SimpleBooks.store;

    public StaplerTestCaseTest() {
        logger.debug("Prepare book store");
    }

    @Override
    protected Object getRoot() {
        return store;
    }

    @Test
    public void testGetBook()
            throws Exception {
        logger.debug("Test: get book");

        HttpTester response = get("/book/Tom/");
        dumpResponse(response);
    }

}
