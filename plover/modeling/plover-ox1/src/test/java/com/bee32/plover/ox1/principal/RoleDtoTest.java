package com.bee32.plover.ox1.principal;

import org.junit.Assert;
import org.junit.Test;

public class RoleDtoTest
        extends Assert {

    @Test
    public void testDepth() {
        RoleDto role = new RoleDto(-1).create();
        assertEquals(255, role.depth);
    }

}
