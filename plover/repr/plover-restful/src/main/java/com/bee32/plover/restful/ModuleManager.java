package com.bee32.plover.restful;

import java.util.ServiceLoader;

import javax.free.IllegalUsageError;
import javax.free.IllegalUsageException;

import com.bee32.plover.arch.IModule;
import com.bee32.plover.arch.locator.LocationLookup;
import com.bee32.plover.arch.locator.ObjectLocatorRegistry;
import com.bee32.plover.pub.oid.OidTree;
import com.bee32.plover.pub.oid.OidUtil;
import com.bee32.plover.pub.oid.OidVector;

public class ModuleManager
        extends OidTree<IModule> {

    private static final long serialVersionUID = 1L;

    private ModuleManager() {
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

            OidTree<IModule> oidTree = get(oid);
            oidTree.set(module);
        }
    }

    /**
     * Get the representational path for a specific object.
     *
     * @param obj
     *            The object to looked up.
     * @return The reversed path with module oid prefixed. Returns <code>null</code> if
     *         <code>obj</code> isn't locatable.
     * @throws IllegalUsageException
     *             If <code>obj</code> isn't possessed by any module.
     */
    public String getReversedPath(Object obj) {
        LocationLookup lookup = ObjectLocatorRegistry.getInstance().lookup(obj, null);
        if (lookup == null)
            return null;

        String location = lookup.joinLocation();
        Object module = lookup.getObject();
        if (!(module instanceof IModule)) {
            throw new IllegalUsageException("Outmost isn't a module");
        }

        Class<?> moduleClass = module.getClass();
        OidVector oid = OidUtil.getOid(moduleClass);
        if (oid == null)
            throw new IllegalUsageException("Module OID doesn't defined: " + moduleClass);

        String uri = oid.toPath() + "/" + location;
        return uri;
    }

    private static final ModuleManager instance = new ModuleManager();

    public static ModuleManager getInstance() {
        return instance;
    }

}
