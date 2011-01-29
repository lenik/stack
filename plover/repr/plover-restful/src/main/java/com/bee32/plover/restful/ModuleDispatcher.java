package com.bee32.plover.restful;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ServiceLoader;

import javax.free.IllegalUsageError;
import javax.servlet.ServletException;

import org.kohsuke.stapler.Dispatcher;
import org.kohsuke.stapler.RequestImpl;
import org.kohsuke.stapler.ResponseImpl;
import org.kohsuke.stapler.StaplerSupport;

import com.bee32.plover.arch.IModule;
import com.bee32.plover.arch.Module;
import com.bee32.plover.pub.oid.OidTree;
import com.bee32.plover.pub.oid.OidUtil;
import com.bee32.plover.pub.oid.OidVector;

public class ModuleDispatcher
        extends Dispatcher {

    private OidTree<IModule> modules;

    public ModuleDispatcher() {
        this.modules = new OidTree<IModule>();
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

            modules.get(oid).set(module);
        }
    }

    @Override
    public boolean dispatch(RequestImpl req, ResponseImpl rsp, Object node)
            throws IOException, ServletException, IllegalAccessException, InvocationTargetException {

        if (node != null)
            return false;

        String[] tokens = req.tokens.tokens;
        int index = req.tokens.idx;

        OidTree<IModule> tree = modules;
        while (index < tokens.length) {
            String token = tokens[index++];

            if (OidUtil.isNumber(token)) {
                int ord = Integer.parseInt(token);
                if (!tree.contains(ord))
                    return false;

                tree = tree.get(ord);
                IModule module = tree.get();
                if (module != null) {
                    req.tokens.idx = index;

                    if (traceable())
                        traceEval(req, rsp, node, "Oid/Module");

                    StaplerSupport.invoke(req.getStapler(), req, rsp, module);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "module-dispatcher";
    }

}
