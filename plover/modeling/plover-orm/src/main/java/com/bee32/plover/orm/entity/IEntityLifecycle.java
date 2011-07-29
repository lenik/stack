package com.bee32.plover.orm.entity;

import com.bee32.plover.orm.util.ErrorResult;

public interface IEntityLifecycle {

    void entityCreate();

    ErrorResult entityValidate();

    ErrorResult entityCheckModify();

    ErrorResult entityCheckDelete();

    void entityUpdated();

    void entityDeleted();

}
