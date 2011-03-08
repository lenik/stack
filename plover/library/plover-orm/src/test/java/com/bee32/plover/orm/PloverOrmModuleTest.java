package com.bee32.plover.orm;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.junit.Test;

import com.bee32.plover.test.WiredTestCase;

public class PloverOrmModuleTest
        extends WiredTestCase {

    @Inject
    SessionFactory sessionFactory;

    @Test
    public void test() {
        assertNotNull(sessionFactory);
    }

}
