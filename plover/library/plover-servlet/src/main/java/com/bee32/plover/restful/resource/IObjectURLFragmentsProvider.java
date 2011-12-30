package com.bee32.plover.restful.resource;

import java.util.Map;

import com.bee32.plover.arch.IModule;
import com.bee32.plover.arch.ModuleLoader;
import com.bee32.plover.rtx.location.Location;

public interface IObjectURLFragmentsProvider {

    String getURLFragments(Object instance, ObjectURLFragmentType fragmentType);

}

class Delegate
        extends ModuleObjectPageDirectory {

    IObjectURLFragmentsProvider fragmentsProvider;
    Object instance;

    public Delegate(Class<? extends IModule> moduleClass, IObjectURLFragmentsProvider fragmentsProvider, Object instance) {
        super(ModuleLoader.getInstance().getModule(moduleClass));
        if (fragmentsProvider == null)
            throw new NullPointerException("fragmentsProvider");
        if (instance == null)
            throw new NullPointerException("instance");
        this.fragmentsProvider = fragmentsProvider;
        this.instance = instance;
    }

    @Override
    public Location getBaseLocation() {
        return super.getBaseLocation();
    }

    @Override
    protected String getLocalPageForView(String viewName, Map<String, ?> parameters) {
        String fragments = fragmentsProvider.getURLFragments(instance, ObjectURLFragmentType.extraParameters);
        String href = super.getLocalPageForView(viewName, parameters);

    }

    @Override
    protected String getLocalPageForOperation(String operationName, Map<String, ?> parameters) {
        String href = super.getLocalPageForOperation(operationName, parameters);
    }

}