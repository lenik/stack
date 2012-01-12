package com.bee32.plover.orm.access;

import com.bee32.plover.orm.entity.Entity;

public class EntityIntegrityException
        extends PloverDataAccessException {

    private static final long serialVersionUID = 1L;

    Object entity;

    public EntityIntegrityException(Object entity) {
        super(makemsg(entity, null));
        this.entity = entity;
    }

    public EntityIntegrityException(Object entity, Throwable cause) {
        super(makemsg(entity, null), cause);
        this.entity = entity;
    }

    public EntityIntegrityException(String msg, Object entity) {
        super(makemsg(entity, msg));
        this.entity = entity;
    }

    public EntityIntegrityException(String msg, Object entity, Throwable cause) {
        super(makemsg(entity, msg), cause);
        this.entity = entity;
    }

    static String makemsg(Object obj, String message) {
        StringBuilder sb = new StringBuilder();
        if (message != null)
            sb.append(message);
        if (obj != null) {
            sb.append(": ");
            if (obj instanceof Entity<?>) {
                Entity<?> entity = (Entity<?>) obj;
                sb.append(entity.getEntryLabel());
            } else {
                sb.append(obj);
            }
        }
        return sb.toString();
    }

}
