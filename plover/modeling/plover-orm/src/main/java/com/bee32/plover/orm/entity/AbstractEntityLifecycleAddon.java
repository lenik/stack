package com.bee32.plover.orm.entity;

import com.bee32.plover.orm.util.ErrorResult;

public abstract class AbstractEntityLifecycleAddon
        implements IEntityLifecycleAddon {

    @Override
    public void entityCreate(Entity<?> entity) {
    }

    @Override
    public ErrorResult entityValidate(Entity<?> entity) {
        return null;
    }

    @Override
    public ErrorResult entityCheckModify(Entity<?> entity) {
        return null;
    }

    @Override
    public ErrorResult entityCheckDelete(Entity<?> entity) {
        return null;
    }

    @Override
    public void entityUpdated(Entity<?> entity) {
    }

    @Override
    public void entityDeleted(Entity<?> entity) {
    }

}
