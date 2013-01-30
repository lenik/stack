package com.bee32.ape.engine.beans;

import com.bee32.plover.inject.ServiceTemplate;

@ServiceTemplate
public abstract class AbstractPec
        implements IProcessEngineConfigurer {

    @Override
    public int getPriority() {
        return 0;
    }

}
