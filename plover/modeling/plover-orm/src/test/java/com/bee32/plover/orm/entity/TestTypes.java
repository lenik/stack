package com.bee32.plover.orm.entity;

interface IFoo
        extends IEntity<Integer> {
    @Override
    Integer getId();
}

class Foo
        extends EntityAuto<Integer>
        implements IFoo {

    private static final long serialVersionUID = 1L;

}

interface Bar
        extends IEntity<Integer> {

    @Override
    Integer getId();

}

class BarImpl
        extends EntityAuto<Integer>
        implements Bar {

    private static final long serialVersionUID = 1L;

}

class FooRepo
        extends EmptyEntityRepository<Foo, Integer> {
}

class BarImplRepo
        extends EmptyEntityRepository<BarImpl, Integer> {
}
