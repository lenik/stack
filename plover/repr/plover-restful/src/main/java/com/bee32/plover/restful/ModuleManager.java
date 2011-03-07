package com.bee32.plover.restful;

import javax.free.IllegalUsageError;
import javax.free.IllegalUsageException;

import com.bee32.plover.arch.IModule;
import com.bee32.plover.arch.ModuleLoader;
import com.bee32.plover.arch.naming.LookupChain;
import com.bee32.plover.arch.naming.ReverseLookupRegistry;
import com.bee32.plover.pub.oid.OidTree;
import com.bee32.plover.pub.oid.OidUtil;
import com.bee32.plover.pub.oid.OidVector;

public class ModuleManager
        extends OidTree<IModule> {

    private static final long serialVersionUID = 1L;

    private ModuleManager() {
        super(IModule.class, null);

        installModules();
    }

    void installModules() {
        for (IModule module : ModuleLoader.getModules()) {
            Class<? extends IModule> moduleClass = module.getClass();

            OidVector oid = OidUtil.getOid(moduleClass);
            if (oid == null)
                throw new IllegalUsageError("No OID defined on module " + moduleClass);

            OidTree<IModule> oidTree = get(oid);
            oidTree.set(module);

            // re-attach the module to module manager.
            module.setParent(this);
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
        LookupChain lookup = ReverseLookupRegistry.getInstance().lookup(obj, null);
        if (lookup == null)
            return null;

        String location = lookup.join();
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
