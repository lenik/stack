package com.bee32.plover.orm.entity;

import java.util.ServiceLoader;

import com.bee32.plover.orm.util.ErrorResult;

public class EntityLifecycleAddons
        implements IEntityLifecycleAddon {

    static final ServiceLoader<IEntityLifecycleAddon> addonLoader;
    static {
        addonLoader = ServiceLoader.load(IEntityLifecycleAddon.class);
    }

    @Override
    public void entityCreate(Entity<?> entity) {
        for (IEntityLifecycleAddon addon : addonLoader)
            addon.entityCreate(entity);
    }

    @Override
    public ErrorResult entityValidate(Entity<?> entity) {
        ErrorResult lastResult = null;
        for (IEntityLifecycleAddon addon : addonLoader) {
            ErrorResult errorResult = addon.entityValidate(entity);
            if (errorResult != null && errorResult.isFailed())
                return errorResult;
            else
                lastResult = errorResult;
        }
        return lastResult;
    }

    @Override
    public ErrorResult entityCheckModify(Entity<?> entity) {
        ErrorResult lastResult = null;
        for (IEntityLifecycleAddon addon : addonLoader) {
            ErrorResult errorResult = addon.entityCheckModify(entity);
            if (errorResult != null && errorResult.isFailed())
                return errorResult;
            else
                lastResult = errorResult;
        }
        return lastResult;
    }

    @Override
    public ErrorResult entityCheckDelete(Entity<?> entity) {
        ErrorResult lastResult = null;
        for (IEntityLifecycleAddon addon : addonLoader) {
            ErrorResult errorResult = addon.entityCheckDelete(entity);
            if (errorResult != null && errorResult.isFailed())
                return errorResult;
            else
                lastResult = errorResult;
        }
        return lastResult;
    }

    @Override
    public void entityUpdated(Entity<?> entity) {
        for (IEntityLifecycleAddon addon : addonLoader)
            addon.entityUpdated(entity);
    }

    @Override
    public void entityDeleted(Entity<?> entity) {
        for (IEntityLifecycleAddon addon : addonLoader)
            addon.entityDeleted(entity);
    }

    static final EntityLifecycleAddons INSTANCE = new EntityLifecycleAddons();

    public static EntityLifecycleAddons getInstance() {
        return INSTANCE;
    }

}
