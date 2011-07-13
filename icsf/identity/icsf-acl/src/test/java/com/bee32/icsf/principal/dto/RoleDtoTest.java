package com.bee32.icsf.principal.dto;

import org.junit.Assert;
import org.junit.Test;

public class RoleDtoTest
        extends Assert {

    @Test
    public void testDepth() {
        RoleDto role = new RoleDto(-1);
        assertEquals(255, role.depth);
    }

}
