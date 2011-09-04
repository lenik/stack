package com.bee32.plover.arch;

import com.bee32.plover.arch.util.IPriority;

public interface IApplicationLifecycle
        extends IPriority {

    void initialize();

    void terminate();

}
