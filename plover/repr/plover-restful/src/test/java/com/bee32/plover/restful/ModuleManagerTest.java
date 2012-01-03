package com.bee32.plover.restful;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.plover.arch.naming.ReverseLookupRegistry;
import com.bee32.plover.pub.oid.OidUtil;
import com.bee32.plover.pub.oid.OidVector;
import com.bee32.plover.restful.builtin.CoreInfoModule;

/**
 * Ignore this test, cuz ModuleManager should work in spring mode.
 *
 * NOTICE: Enable this test case until dynamic test suites selection is supported.
 */
public class ModuleManagerTest
        extends Assert {

    @Test
    public void testChildNames() {
        ModuleIndex mm = ModuleIndex.getStaticInstance();
        Collection<String> names = mm.getChildNames();
        for (String name : names)
            System.out.println(name);
    }

    @Test
    public void testReverse() {
        ModuleIndex mm = ModuleIndex.getStaticInstance();

        OidVector coreOid = OidUtil.getOid(CoreInfoModule.class);
        String path = coreOid.toPath(); // "3/12/2"

        Object child = mm.getChild(path);
        assertNotNull(child);

        String childName = mm.getChildName(child);
        assertEquals(path, childName);

        ReverseLookupRegistry rl = ReverseLookupRegistry.getInstance();
        String registryLookup = rl.getLocation(child);
        assertEquals(path, registryLookup);
    }

}
