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

interface IFoo
        extends IEntity<Integer> {
    @Override
    Integer getId();
}

class Foo
        extends EntityBean<Integer>
        implements IFoo {

    private static final long serialVersionUID = 1L;

}

interface Bar
        extends IEntity<Integer> {

    @Override
    Integer getId();

}

class BarImpl
        extends EntityBean<Integer>
        implements Bar {

    private static final long serialVersionUID = 1L;

}

class FooRepo
        extends EmptyEntityRepository<Foo, Integer> {

    public FooRepo() {
        super(Foo.class, Integer.class);
    }

}

class BarImplRepo
        extends EmptyEntityRepository<BarImpl, Integer> {
    public BarImplRepo() {
        super(BarImpl.class, Integer.class);
    }

}
