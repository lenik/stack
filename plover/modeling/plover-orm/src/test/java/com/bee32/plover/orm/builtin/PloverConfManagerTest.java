package com.bee32.plover.orm.builtin;

import javax.inject.Inject;

import org.junit.Test;

import com.bee32.plover.orm.PloverORMUnit;
import com.bee32.plover.orm.unit.Using;
import com.bee32.plover.orm.util.WiredDaoTestCase;

@Using(PloverORMUnit.class)
public class PloverConfManagerTest
        extends WiredDaoTestCase {

    @Inject
    PloverConfManager confManager;

    @Test
    public void testSetConf() {
        confManager.set("test", "hello", "world");
        String value = confManager.get("test", "hello");
        assertEquals("world", value);
    }

    @Test
    public void testNotExist() {
        String value = confManager.get("test", "not-a-existed-key");
        assertNull(value);
    }

}
