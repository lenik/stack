package com.bee32.icsf.access.shield;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.bee32.icsf.access.Permission;
import com.bee32.plover.orm.entity.EasTxWrapper;
import com.bee32.plover.orm.entity.Entity;

public abstract class EasTxWrapperCat<E extends Entity<? extends K>, K extends Serializable>
        extends EasTxWrapper<E, K> {

    static final Permission READ = new Permission(Permission.READ);
    static final Permission WRITE = new Permission(Permission.WRITE);
    static final Permission CREATE = new Permission(Permission.CREATE);
    static final Permission DELETE = new Permission(Permission.DELETE);

    @Override
    protected void checkLoad() {
        require(getEntityType(), null, READ);
    }

    @Override
    protected void checkCount() {
        // require(getEntityType(), READ);
    }

    @Override
    protected void checkList() {
        require(getEntityType(), null, READ);
    }

    @Override
    protected void checkMerge(E entity) {
        List<E> entities = Arrays.asList(entity);
        require(getEntityType(), entities, WRITE);
    }

    @Override
    protected void checkSave(Collection<? extends E> entities) {
        require(getEntityType(), entities, CREATE);
    }

    @Override
    protected void checkUpdate(Collection<? extends E> entities) {
        require(getEntityType(), entities, WRITE);
    }

    @Override
    protected void checkDelete() {
        require(getEntityType(), null, DELETE);
    }

    protected abstract void require(Class<? extends Entity<?>> entityType, Collection<? extends E> entities,
            Permission requiredPermission);

}
