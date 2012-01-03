package com.bee32.plover.restful;

import javax.free.IllegalUsageError;
import javax.free.IllegalUsageException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.bee32.plover.arch.IModule;
import com.bee32.plover.arch.IModuleManager;
import com.bee32.plover.arch.ModuleManager;
import com.bee32.plover.arch.naming.LookupChain;
import com.bee32.plover.arch.naming.ReverseLookupRegistry;
import com.bee32.plover.pub.oid.OidTree;
import com.bee32.plover.pub.oid.OidUtil;
import com.bee32.plover.pub.oid.OidVector;

@Component
@Lazy
public class ModuleIndex
        extends OidTree<IModule>
        implements InitializingBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ModuleIndex.class);

    private transient IModuleManager moduleManager = ModuleManager.getInstance();

    private transient boolean installed;

    static ModuleIndex _lastInstance;

    public ModuleIndex() {
        super(IModule.class, null);

        // if (_lastInstance != null)
        // throw new IllegalStateException("ModuleManager should be singleton.");
        // _lastInstance = this;
    }

    public ModuleIndex(IModuleManager moduleManager) {
        super(IModule.class, null);

        if (moduleManager == null)
            throw new NullPointerException("moduleManager");

        this.moduleManager = moduleManager;

        afterPropertiesSet();
    }

    @Override
    public synchronized void afterPropertiesSet() {
        installModules();
    }

    protected synchronized void installModules() {
        if (installed)
            return;
        for (IModule module : moduleManager.getModules()) {
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
        installed = true;
    }

    public void activate() {
        moduleManager.activate();
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

    private static ModuleIndex instance;

    public static ModuleIndex getStaticInstance() {
        if (instance == null) {
            synchronized (ModuleIndex.class) {
                if (instance == null) {
                    instance = new ModuleIndex(ModuleManager.getInstance());
                }
            }
        }
        return instance;
    }

}
