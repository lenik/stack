package com.bee32.plover.restful;

import org.junit.Test;
import org.mortbay.jetty.testing.HttpTester;

public class StaplerTestCaseTest
        extends StaplerTestCase {

    BookStore store;

    public StaplerTestCaseTest() {
        logger.debug("Prepare book store");
        store = new BookStore();

        Book tom = new Book("Tom", "A great story");
        Book jerry = new Book("Jerry", "A wonderful cartoon book");

        store.addBook("Tom", tom);
        store.addBook("Jerry", jerry);

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
