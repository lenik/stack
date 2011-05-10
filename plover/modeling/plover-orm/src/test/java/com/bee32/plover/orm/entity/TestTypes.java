package com.bee32.plover.orm.entity;

import com.bee32.plover.orm.ext.color.EntityBean;

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
}

class BarImplRepo
        extends EmptyEntityRepository<BarImpl, Integer> {
}
