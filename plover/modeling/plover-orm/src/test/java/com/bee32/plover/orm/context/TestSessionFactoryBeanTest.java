package com.bee32.plover.orm.context;

import org.junit.Test;

import com.bee32.plover.orm.util.WiredDaoTestCase;

public class TestSessionFactoryBeanTest
        extends WiredDaoTestCase {

    @Test
    public void gotConfig() {
        assertNotNull(dataManager);
    }

}
