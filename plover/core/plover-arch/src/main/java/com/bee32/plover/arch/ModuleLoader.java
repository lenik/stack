package com.bee32.plover.arch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ServiceLoader;
import java.util.TreeSet;

public class ModuleLoader
        extends AbstractModuleLoader {

    @Override
    protected Collection<IModule> reload() {
        List<IModule> modules = new ArrayList<IModule>();

        ServiceLoader<IModule> moduleLoader;
        moduleLoader = ServiceLoader.load(IModule.class);

        for (IModule module : moduleLoader)
            modules.add(module);

        return modules;
    }

    protected TreeSet<IModulePostProcessor> getPostProcessors() {
        TreeSet<IModulePostProcessor> modulePostProcessors = new TreeSet<IModulePostProcessor>(
                ModulePostProcessorComparator.getInstance());

        for (IModulePostProcessor modulePostProcessor : ServiceLoader.load(IModulePostProcessor.class)) {
            modulePostProcessors.add(modulePostProcessor);
        }

        return modulePostProcessors;
    }

    static final ModuleLoader instance = new ModuleLoader();

    public static ModuleLoader getInstance() {
        return instance;
    }

}
