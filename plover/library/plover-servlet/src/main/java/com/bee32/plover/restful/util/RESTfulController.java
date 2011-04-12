package com.bee32.plover.restful.util;

import com.bee32.plover.restful.IRESTfulRequest;

/**
 * @param T
 *            Target object type
 */
public abstract class RESTfulController<T> {

    private Class<T> clazz;

    public RESTfulController(Class<T> clazz) {
        if (clazz == null)
            throw new NullPointerException("clazz");
        this.clazz = clazz;
    }

    protected T cast(IRESTfulRequest req) {
        Object target = req.getTarget();

        if (target == null)
            return null;

        return clazz.cast(target);
    }

}
