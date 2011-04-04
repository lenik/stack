package com.bee32.plover.restful.book;

import org.junit.Test;

import com.bee32.plover.orm.util.WiredDaoTestCase;

public class SessionFactoryContextTest
        extends WiredDaoTestCase {

    @Test
    public void testContext() {
        assertNotNull(getSessionFactory());
    }

}
