package com.bee32.plover.inject;

import com.bee32.plover.arch.util.IOrdered;

public interface IActivatorService
        extends IOrdered {

    void activate();

    void deactivate();

}
