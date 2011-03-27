package com.bee32.plover.arch.util.res;

import java.util.EventListener;

public interface IPropertyRefreshListener
        extends EventListener {

    void propertyRefresh(PropertyRefreshEvent event);

}
