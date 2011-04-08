package com.bee32.plover.restful.util;

import com.bee32.plover.restful.IRestfulRequest;

/**
 * @param T
 *            Target object type
 */
public abstract class RestfulController<T> {

    private Class<T> clazz;

    public RestfulController(Class<T> clazz) {
        if (clazz == null)
            throw new NullPointerException("clazz");
        this.clazz = clazz;
    }

    protected T cast(IRestfulRequest req) {
        Object target = req.getTarget();

        if (target == null)
            return null;

        return clazz.cast(target);
    }

}
