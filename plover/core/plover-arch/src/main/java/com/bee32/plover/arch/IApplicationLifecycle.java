package com.bee32.plover.arch;

import com.bee32.plover.arch.util.IPriority;

/**
 * This application is nothing about Spring ApplicationContext.
 *
 * @see Application
 */
public interface IApplicationLifecycle
        extends IPriority {

    void initialize();

    void terminate();

}
