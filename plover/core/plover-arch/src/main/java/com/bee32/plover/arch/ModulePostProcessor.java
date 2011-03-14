package com.bee32.plover.arch;

public abstract class ModulePostProcessor
        extends Component
        implements IModulePostProcessor {

    @Override
    public int getPriority() {
        return 0;
    }

}
