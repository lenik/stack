package com.bee32.plover.repr.tree;

import javax.free.AbstractImmediateIterator;

public class AbstractTreeIterator<T>
        extends AbstractImmediateIterator<T>
        implements TreeIterator<T> {

    @Override
    public T next() {
        return null;
    }

    @Override
    public T next(boolean recursive) {
        return null;
    }

    @Override
    public boolean dropToFrame() {
        return false;
    }

}
