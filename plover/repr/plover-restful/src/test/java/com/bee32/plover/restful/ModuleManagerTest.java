package com.bee32.plover.restful;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.plover.arch.naming.ReverseLookupRegistry;
import com.bee32.plover.pub.oid.OidUtil;
import com.bee32.plover.pub.oid.OidVector;
import com.bee32.plover.restful.builtin.CoreInfoModule;

public class ModuleManagerTest
        extends Assert {

    @Test
    public void testChildNames() {
        ModuleManager mm = ModuleManager.getInstance();
        Collection<String> names = mm.getChildNames();
        for (String name : names)
            System.out.println(name);
    }

    @Test
    public void testReverse() {
        ModuleManager mm = ModuleManager.getInstance();

        OidVector coreOid = OidUtil.getOid(CoreInfoModule.class);
        String path = coreOid.toPath(); // "3/12/2"

        Object child = mm.getChild(path);
        assertNotNull(child);

        String reversed = mm.getChildName(child);

        assertEquals(path, reversed);

        ReverseLookupRegistry rl = ReverseLookupRegistry.getInstance();
        String location = rl.getLocation(child);
        assertEquals("2", location);
    }

}
