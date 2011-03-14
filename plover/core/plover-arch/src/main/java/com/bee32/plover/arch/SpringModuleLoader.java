package com.bee32.plover.arch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.inject.Inject;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class SpringModuleLoader
        extends AbstractModuleLoader {

    @Inject
    private ApplicationContext applicationContext;

    /**
     * XXX - Should listen to application events?
     */
    @Override
    protected Collection<IModule> reload() {
        if (applicationContext == null)
            throw new IllegalStateException("Application context is null");

        List<IModule> modules = new ArrayList<IModule>();

        Map<String, IModule> mmap = applicationContext.getBeansOfType(IModule.class);

        // Ignore the bean names.
        for (IModule module : mmap.values())
            modules.add(module);

        return modules;
    }

    @Override
    protected TreeSet<IModulePostProcessor> getPostProcessors() {
        if (applicationContext == null)
            throw new IllegalStateException("Application context is null");

        TreeSet<IModulePostProcessor> modulePostProcessors = new TreeSet<IModulePostProcessor>(
                ModulePostProcessorComparator.getInstance());

        Map<String, IModulePostProcessor> ppMap = applicationContext.getBeansOfType(IModulePostProcessor.class);

        for (IModulePostProcessor modulePostProcessor : ppMap.values())
            modulePostProcessors.add(modulePostProcessor);

        return modulePostProcessors;
    }

}
