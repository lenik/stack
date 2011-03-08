package com.bee32.plover.arch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.TreeMap;

public class ServiceModuleLoader
        implements IModuleLoader {

    private static List<IModule> modules;
    private static Map<String, IModule> moduleMap;

    static synchronized void _load() {
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

    @Override
    public void load() {
        _load();
    }

    public Iterable<IModule> getModules() {
        load();
        return modules;
    }

    public Map<String, IModule> getModuleMap() {
        load();
        return moduleMap;
    }

    static final ServiceModuleLoader instance = new ServiceModuleLoader();

    public static ServiceModuleLoader getInstance() {
        return instance;
    }

}
