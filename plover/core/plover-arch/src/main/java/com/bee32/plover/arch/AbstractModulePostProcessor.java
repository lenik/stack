package com.bee32.plover.arch;

public abstract class AbstractModulePostProcessor
        extends Component
        implements IModulePostProcessor {

    @Override
    public int getPriority() {
        return 0;
    }

}
