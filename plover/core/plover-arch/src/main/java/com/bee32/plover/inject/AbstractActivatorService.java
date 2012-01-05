package com.bee32.plover.inject;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import com.bee32.plover.arch.Component;

/**
 * Activator service.
 */
@ServiceTemplate
public abstract class AbstractActivatorService
        extends Component
        implements IActivatorService {

    protected ApplicationContext appctx;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.appctx = applicationContext;
    }

    @Override
    public void activate() {
    }

    @Override
    public void deactivate() {
    }

}
