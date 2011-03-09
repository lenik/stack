package com.bee32.icsf.principal.dao;

import javax.inject.Inject;

import org.junit.Test;

import com.bee32.plover.test.WiredAssembledTestCase;

public class UserDaoTest
        extends WiredAssembledTestCase {

    @Inject
    UserDao users;

    @Test
    public void testInjection() {
        assertNotNull(users);
    }

}
