package com.bee32.plover.restful;

import java.util.ServiceLoader;

import javax.free.IllegalUsageError;

import com.bee32.plover.arch.IModule;
import com.bee32.plover.arch.Module;
import com.bee32.plover.pub.oid.OidTree;
import com.bee32.plover.pub.oid.OidUtil;
import com.bee32.plover.pub.oid.OidVector;

public class ModuleManager
        extends OidTree<IModule> {

    private static final long serialVersionUID = 1L;

    public ModuleManager() {
        refreshModules();
    }

    private transient ServiceLoader<Module> moduleLoader;

    void refreshModules() {
        moduleLoader = ServiceLoader.load(Module.class);

        for (Module module : moduleLoader) {
            Class<? extends Module> moduleClass = module.getClass();

            OidVector oid = OidUtil.getOid(moduleClass);
            if (oid == null)
                throw new IllegalUsageError("No OID defined on module " + moduleClass);

            get(oid).set(module);
        }
    }

    private static final ModuleManager instance = new ModuleManager();

    public static ModuleManager getInstance() {
        return instance;
    }

}
