package com.bee32.plover.orm.entity;

import com.bee32.plover.orm.util.ErrorResult;

/**
 * @see IEntityLifecycle
 */
public interface IEntityLifecycleAddon {

    void entityCreate(Entity<?> entity);

    ErrorResult entityValidate(Entity<?> entity);

    ErrorResult entityCheckModify(Entity<?> entity);

    ErrorResult entityCheckDelete(Entity<?> entity);

    void entityUpdated(Entity<?> entity);

    void entityDeleted(Entity<?> entity);

}
