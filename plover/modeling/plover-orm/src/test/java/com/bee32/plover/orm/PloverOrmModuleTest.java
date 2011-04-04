package com.bee32.plover.orm;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.junit.Test;

import com.bee32.plover.orm.util.WiredDaoTestCase;

public class PloverOrmModuleTest
        extends WiredDaoTestCase {

    @Inject
    SessionFactory sessionFactory;

    @Test
    public void test() {
        assertNotNull(sessionFactory);
    }

}
