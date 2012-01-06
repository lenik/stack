package com.bee32.plover.inject;

import com.bee32.plover.arch.Component;

/**
 * Activator service.
 */
@ServiceTemplate
public abstract class AbstractActivatorService
        extends Component
        implements IActivatorService {

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public void activate() {
    }

    @Override
    public void deactivate() {
    }

}
