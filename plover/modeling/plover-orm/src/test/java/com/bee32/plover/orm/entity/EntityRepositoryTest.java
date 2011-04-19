package com.bee32.plover.orm.entity;

import org.junit.Assert;
import org.junit.Test;

public class EntityRepositoryTest
        extends Assert {

    @Test
    public void testGetEntityClassIFoo() {
        FooRepo repo = new FooRepo();
        assertEquals(Foo.class, repo.getEntityType());
    }

    @Test
    public void testGetEntityClassBarImpl() {
        BarImplRepo repo = new BarImplRepo();
        assertEquals(BarImpl.class, repo.getEntityType());
    }

}
