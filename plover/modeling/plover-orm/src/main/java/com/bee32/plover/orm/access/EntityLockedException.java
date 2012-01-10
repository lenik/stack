package com.bee32.plover.orm.access;

public class EntityLockedException
        extends EntityIntegrityException {

    private static final long serialVersionUID = 1L;

    public EntityLockedException(Object entity, Throwable cause) {
        super(entity, cause);
    }

    public EntityLockedException(Object entity) {
        super(entity);
    }

    public EntityLockedException(String msg, Object entity, Throwable cause) {
        super(msg, entity, cause);
    }

    public EntityLockedException(String msg, Object entity) {
        super(msg, entity);
    }

}
