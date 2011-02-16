package com.bee32.sems.security.access.builtins;

import java.util.Locale;

import org.junit.Test;

public class WorldPrincipalTest {

    WorldPrincipal world;

    public WorldPrincipalTest() {
        Locale.setDefault(new Locale("no-default-locale"));
        world = WorldPrincipal.getInstance();
    }

    @Test
    public void testInfo()
            throws Exception {
//        world.getDescription();
    }

}
