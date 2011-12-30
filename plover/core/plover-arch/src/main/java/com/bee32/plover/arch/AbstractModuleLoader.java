package com.bee32.plover.arch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.free.ClassLocal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractModuleLoader
        extends Component
        implements IModuleLoader {

    static Logger logger = LoggerFactory.getLogger(AbstractModuleLoader.class);

    private boolean loaded;
    private boolean activated;

    private Collection<IModule> modules;
    private Map<String, IModule> moduleNameMap;
    private Map<Class<?>, IModule> moduleClassMap;

    private Collection<IModulePostProcessor> modulePostProcessors;

    @Override
    public final synchronized void load() {
        if (!loaded) {
            modules = reload();
            moduleNameMap = new TreeMap<String, IModule>();
            moduleClassMap = new ClassLocal<IModule>();
            for (IModule module : modules) {
                String name = module.getName();
                moduleNameMap.put(name, module);
                moduleClassMap.put(module.getClass(), module);
            }
            loaded = true;
        }
    }

    @Override
    public final synchronized void activate() {
        if (!activated) {

            load();

            List<IModule> failedModules = new ArrayList<IModule>();

            if (modulePostProcessors == null)
                modulePostProcessors = getPostProcessors();

            for (IModulePostProcessor modulePostProcessor : modulePostProcessors) {

                logger.info("Apply module post-processor: " + modulePostProcessor);

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
                    moduleNameMap.remove(failedModuleName);
                    moduleClassMap.remove(failedModule.getClass());
                }
            }

            activated = true;
        }
    }

    protected abstract Collection<IModule> reload();

    public Iterable<IModule> getModules() {
        load();
        return modules;
    }

    public Map<String, IModule> getModuleMap() {
        load();
        return moduleNameMap;
    }

    @Override
    public IModule getModule(String moduleName) {
        return moduleNameMap.get(moduleName);
    }

    @Override
    public IModule getModule(Class<? extends IModule> moduleClass) {
        return moduleClassMap.get(moduleClass);
    }

    protected abstract TreeSet<IModulePostProcessor> getPostProcessors();

}
