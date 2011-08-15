package com.bee32.plover.inject;

import org.springframework.context.ApplicationContextAware;

public interface IActivatorService
        extends ApplicationContextAware {

    void activate();

    void deactivate();

}
