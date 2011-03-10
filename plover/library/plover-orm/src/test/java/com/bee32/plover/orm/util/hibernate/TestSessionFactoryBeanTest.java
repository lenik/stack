package com.bee32.plover.orm.util.hibernate;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.junit.Test;

import com.bee32.plover.test.WiredTestCase;

public class TestSessionFactoryBeanTest
        extends WiredTestCase {

    @Inject
    SessionFactory sessionFactory;

    @Test
    public void gotAnySessionFactory() {
        assertNotNull(sessionFactory);
    }

}
