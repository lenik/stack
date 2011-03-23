package com.bee32.plover.arch.util.res;

public interface IPropertyDispatcher {

    IPropertyDispatchStrategy getStrategy();

    Object getBoundResource();

    /**
     * Do the bound dispatch.
     */
    void dispatch();

    /**
     * The same as {@link #dispatch()}, however, only dispatch once.
     */
    void require();

}
