package com.bee32.plover.arch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractModuleLoader
        implements IModuleLoader {

    static Logger logger = LoggerFactory.getLogger(AbstractModuleLoader.class);

    private Collection<IModule> modules;
    private Map<String, IModule> moduleMap;

    private Collection<IModulePostProcessor> modulePostProcessors;

    @Override
    public final synchronized void load() {
        if (modules == null) {

            modules = reload();

            moduleMap = new TreeMap<String, IModule>();
            for (IModule module : modules) {
                String name = module.getName();
                moduleMap.put(name, module);
            }

            List<IModule> failedModules = new ArrayList<IModule>();

            if (modulePostProcessors == null)
                modulePostProcessors = getPostProcessors();

            for (IModulePostProcessor modulePostProcessor : modulePostProcessors) {
                for (IModule module : modules) {
                    try {
                        modulePostProcessor.afterModuleLoaded(module);
                    } catch (Exception e) {
                        logger.error("Failed to load " + module + ", queued to be removed", e);
                        failedModules.add(module);
                    }
                }
            }

            if (!failedModules.isEmpty()) {
                int index = failedModules.size();
                while (--index >= 0) {
                    IModule failedModule = failedModules.get(index);
                    String failedModuleName = failedModule.getName();

                    logger.info("Remove failed module: " + failedModuleName);

                    modules.remove(failedModule);
                    moduleMap.remove(failedModuleName);
                }
            }
        }

    }

    protected abstract Collection<IModule> reload();

    public Iterable<IModule> getModules() {
        load();
        return modules;
    }

    public Map<String, IModule> getModuleMap() {
        load();
        return moduleMap;
    }

    protected abstract TreeSet<IModulePostProcessor> getPostProcessors();

}
