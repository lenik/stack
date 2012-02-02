package com.bee32.plover.orm.util;

import com.bee32.plover.arch.IModule;
import com.bee32.plover.arch.Module;
import com.bee32.plover.arch.ModuleManager;
import com.bee32.plover.arch.type.AbstractParameterizedType;
import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.restful.resource.IObjectPageDirectory;
import com.bee32.plover.restful.resource.IObjectURLFragmentsProvider;
import com.bee32.plover.restful.resource.ObjectURLFragmentType;

public class ParameterizedEntityType
        extends AbstractParameterizedType
        implements IObjectURLFragmentsProvider {

    private final IModule module;
    protected final Class<? extends Entity<?>> entityType;

    public ParameterizedEntityType(Class<? extends Module> moduleClass, Class<? extends Entity<?>> entityType) {
        this(ModuleManager.getInstance().getModule(moduleClass), entityType);
    }

    public ParameterizedEntityType(IModule module, Class<? extends Entity<?>> entityType) {
        super(entityType);
        if (module == null)
            throw new NullPointerException("module");
        this.module = module;
        this.entityType = entityType;
    }

    @Override
    public String getDisplayTypeName(Object instance) {
        return ClassUtil.getTypeName(entityType);
    }

    @Override
    public Object getURLFragment(Object instance, ObjectURLFragmentType fragmentType) {
        return null;
    }

    protected class PageDirCompletion
            extends ModuleEntityPageDirectory.Completion {

        public PageDirCompletion(Object instance) {
            super(module, ParameterizedEntityType.this, instance);
        }

    }

    @Override
    public <T> T getFeature(Object instance, Class<T> featureClass) {
        if (featureClass.equals(IObjectPageDirectory.class)) {
            IObjectPageDirectory pageDir = new PageDirCompletion(instance);
            return featureClass.cast(pageDir);
        }
        return super.getFeature(instance, featureClass);
    }

}
