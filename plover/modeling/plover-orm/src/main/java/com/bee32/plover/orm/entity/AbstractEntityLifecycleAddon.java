package com.bee32.plover.orm.entity;

import com.bee32.plover.inject.ServiceTemplate;
import com.bee32.plover.orm.util.ErrorResult;

@ServiceTemplate
public abstract class AbstractEntityLifecycleAddon
        implements IEntityLifecycleAddon {

    @Override
    public ErrorResult entityCreate(Entity<?> entity) {
        return ErrorResult.SUCCESS;
    }

    @Override
    public ErrorResult entityValidate(Entity<?> entity) {
        return ErrorResult.SUCCESS;
    }

    @Override
    public ErrorResult entityCheckModify(Entity<?> entity) {
        return ErrorResult.SUCCESS;
    }

    @Override
    public ErrorResult entityCheckDelete(Entity<?> entity) {
        return ErrorResult.SUCCESS;
    }

    @Override
    public void entityUpdated(Entity<?> entity) {
    }

    @Override
    public void entityDeleted(Entity<?> entity) {
    }

}
