package com.bee32.plover.arch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SpringModuleLoader
        implements IModuleLoader {

    private List<IModule> modules;
    private Map<String, IModule> moduleMap;

    @Inject
    private ApplicationContext applicationContext;

    /**
     * XXX - Should listen to application events?
     */
    @Override
    public synchronized void load() {
        if (modules == null) {
            if (applicationContext == null)
                throw new IllegalStateException("Application context is null");

            modules = new ArrayList<IModule>();
            moduleMap = new TreeMap<String, IModule>();

            Map<String, IModule> mmap = applicationContext.getBeansOfType(IModule.class);

            // Ignore the bean names.
            for (IModule module : mmap.values()) {
                modules.add(module);

                String moduleName = module.getName();
                moduleMap.put(moduleName, module);
            }
        }
    }

    @Override
    public Iterable<IModule> getModules() {
        load();
        return modules;
    }

    @Override
    public Map<String, IModule> getModuleMap() {
        load();
        return moduleMap;
    }

}
