package com.bee32.plover.arch;

import java.util.Map;

import com.bee32.plover.inject.IActivatorService;

public interface IModuleManager
        extends IActivatorService {

    Iterable<IModule> getModules();

    Map<String, IModule> getModuleMap();

    IModule getModule(String moduleName);

    IModule getModule(Class<? extends IModule> moduleClass);

}
