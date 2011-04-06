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
    public void testGetEntityClassBar() {
        BarRepo repo = new BarRepo();
        assertEquals(BarImpl.class, repo.getEntityType());
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
        implements IFoo {

    private static final long serialVersionUID = 1L;

    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public void setId(Integer id) {
    }

}

interface Bar
        extends IEntity<Integer> {

    @Override
    Integer getId();

}

class BarImpl
        implements Bar {

    private static final long serialVersionUID = 1L;

    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public void setId(Integer id) {
    }

}

class FooRepo
        extends EmptyEntityRepository<IFoo, Integer> {

    public FooRepo() {
        super(IFoo.class, Integer.class);
    }

}

class BarRepo
        extends EmptyEntityRepository<Bar, Integer> {

    public BarRepo() {
        super(Bar.class, Integer.class);
    }

}

class BarImplRepo
        extends EmptyEntityRepository<BarImpl, Integer> {
    public BarImplRepo() {
        super(BarImpl.class, Integer.class);
    }

}
