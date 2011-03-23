package com.bee32.plover.arch.util.res;

import java.util.Map;

public interface IPropertyDispatchStrategy {

    Iterable<IPropertyAcceptor> getAcceptors();

    /**
     * @return <code>null</code> If map isn't available.
     */
    Map<String, ? extends IPropertyAcceptor> getAcceptorMap();

    void dispatch(String key, String content);

}
