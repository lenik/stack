package com.bee32.plover.restful;

import com.bee32.plover.arch.IModule;
import com.bee32.plover.pub.oid.OidTree;
import com.bee32.plover.pub.oid.OidUtil;
import com.bee32.plover.pub.oid.OidVector;

import javax.free.IllegalUsageError;
import java.util.ServiceLoader;

public class ModuleManager
        extends OidTree<IModule> {

    private static final long serialVersionUID = 1L;

    public ModuleManager() {
        refreshModules();
    }

    private transient ServiceLoader<IModule> moduleLoader;

    void refreshModules() {
        moduleLoader = ServiceLoader.load(IModule.class);

        for (IModule module : moduleLoader) {
            Class<? extends IModule> moduleClass = module.getClass();

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
