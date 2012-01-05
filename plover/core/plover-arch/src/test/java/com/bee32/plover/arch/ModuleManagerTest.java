package com.bee32.plover.arch;

import org.junit.Assert;
import org.junit.Test;

public class ModuleManagerTest
        extends Assert {

    @Test
    public void testInit() {
        ModuleManager mm = ModuleManager.getInstance();
        for (IModule module : mm.getModules()) {
            System.out.println(module.getName());
        }
    }

}
