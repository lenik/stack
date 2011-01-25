package com.bee32.plover.repr.tree;

import javax.free.ImmediateIterator;

public interface TreeIterator<T>
        extends ImmediateIterator<T> {

    /**
     * Same as non-recursive next.
     */
    @Override
    T next();

    /**
     * Recurse to descendants if there has any.
     */
    T next(boolean recursive);

    /**
     * Back trace.
     */
    boolean dropToFrame();

}
