package com.bee32.plover.restful;

import javax.free.IllegalUsageError;
import javax.free.IllegalUsageException;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.bee32.plover.arch.IModule;
import com.bee32.plover.arch.IModuleLoader;
import com.bee32.plover.arch.ServiceModuleLoader;
import com.bee32.plover.arch.naming.LookupChain;
import com.bee32.plover.arch.naming.ReverseLookupRegistry;
import com.bee32.plover.pub.oid.OidTree;
import com.bee32.plover.pub.oid.OidUtil;
import com.bee32.plover.pub.oid.OidVector;

@Component
public class ModuleManager
        extends OidTree<IModule>
        implements InitializingBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ModuleManager.class);

    @Inject
    private transient IModuleLoader moduleLoader;

    private transient boolean loaded;

    public ModuleManager() {
        super(IModule.class, null);
    }

    public ModuleManager(IModuleLoader moduleLoader) {
        super(IModule.class, null);

        if (moduleLoader == null)
            throw new NullPointerException("moduleLoader");

        this.moduleLoader = moduleLoader;

        afterPropertiesSet();
    }

    @Override
    public synchronized void afterPropertiesSet() {
        if (!loaded) {
            installModules();
            loaded = true;
        }
    }

    protected synchronized void installModules() {
        for (IModule module : moduleLoader.getModules()) {
            Class<? extends IModule> moduleClass = module.getClass();

            OidVector oid = OidUtil.getOid(moduleClass);
            if (oid == null)
                throw new IllegalUsageError("No OID defined on module " + moduleClass);

            logger.info("Load module " + module + " at " + oid);

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

        if (module instanceof IModule) {
            Class<?> moduleClass = module.getClass();
            OidVector oid = OidUtil.getOid(moduleClass);
            if (oid == null)
                throw new IllegalUsageException("Module OID doesn't defined: " + moduleClass);

            location = oid.toPath() + "/" + location;
        } else {
            // Congratulations!
            // We've already went up to the root!
        }

        return location;
    }

    private static final ModuleManager instance;
    static {
        instance = new ModuleManager(ServiceModuleLoader.getInstance());
    }

    public static ModuleManager getInstance() {
        return instance;
    }

}
