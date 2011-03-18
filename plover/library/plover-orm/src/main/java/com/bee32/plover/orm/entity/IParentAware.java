package com.bee32.plover.orm.entity;

public interface IParentAware<E> {

    E getParent();

    void setParent(E parent);

}
