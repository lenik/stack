package com.bee32.plover.arch;

import com.bee32.plover.inject.ServiceTemplate;

@ServiceTemplate
public abstract class ApplicationLifecycle
        implements IApplicationLifecycle {

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void terminate() {
    }

}
