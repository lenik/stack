package com.bee32.plover.arch;

public interface IModulePostProcessor {

    int getPriority();

    void afterModuleLoaded(IModule module)
            throws Exception;

}
