package com.bee32.icsf.access.shield;

import java.io.Serializable;

import com.bee32.icsf.access.Permission;
import com.bee32.plover.orm.entity.EasTxWrapper;
import com.bee32.plover.orm.entity.Entity;

public abstract class EasTxWrapperCat<E extends Entity<? extends K>, K extends Serializable>
        extends EasTxWrapper<E, K> {

    @Override
    protected void checkLoad() {
        require(Permission.READ);
    }

    @Override
    protected void checkCount() {
    }

    @Override
    protected void checkList() {
        require(Permission.LIST);
    }

    @Override
    protected void checkMerge() {
        require(Permission.WRITE);
    }

    @Override
    protected void checkSave() {
        require(Permission.CREATE);
    }

    @Override
    protected void checkUpdate() {
        require(Permission.WRITE);
    }

    @Override
    protected void checkDelete() {
        require(Permission.DELETE);
    }

    protected abstract void require(int permissionBits);

}
