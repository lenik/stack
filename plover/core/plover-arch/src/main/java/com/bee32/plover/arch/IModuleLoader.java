package com.bee32.plover.arch;

import java.util.Map;

public interface IModuleLoader {

    void load();

    Iterable<IModule> getModules();

    Map<String, IModule> getModuleMap();

    void activate();

}
