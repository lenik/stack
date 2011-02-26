package com.bee32.plover.arch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.TreeMap;

public class ModuleLoader {

    private static List<IModule> modules;
    private static Map<String, IModule> moduleMap;

    public static synchronized void load() {
        if (modules == null) {
            modules = new ArrayList<IModule>();
            moduleMap = new TreeMap<String, IModule>();

            ServiceLoader<IModule> moduleLoader;
            moduleLoader = ServiceLoader.load(IModule.class);

            for (IModule module : moduleLoader) {
                modules.add(module);

                String moduleName = module.getName();
                moduleMap.put(moduleName, module);
            }
        }
    }

    public static Iterable<IModule> getModules() {
        return modules;
    }

    public static Map<String, IModule> getModuleMap() {
        load();
        return moduleMap;
    }

}
