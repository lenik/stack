package com.bee32.plover.orm.util;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import com.bee32.plover.orm.entity.Entity;

/**
 * TODO how to install this into hibernate session?
 */
public class _EntityInterceptor
        extends EmptyInterceptor {

    private static final long serialVersionUID = 1L;

    /**
     * 1, return true 是否对性能造成影响？
     *
     * 2, Entity 上另外加一个业务级的 last-modified time.
     */
    @Override
    public boolean onSave(Object _entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        if (_entity instanceof Entity<?>) {
            Entity<?> entity = (Entity<?>) _entity;
            entity.setLastModified(new Date());
            return true;
        }
        return false;
    }

}
