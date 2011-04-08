package com.bee32.plover.restful.util;

import com.bee32.plover.restful.RestfulRequest;

public abstract class RestfulController<T> {

    private Class<T> clazz;

    public RestfulController(Class<T> clazz) {
        if (clazz == null)
            throw new NullPointerException("clazz");
        this.clazz = clazz;
    }

    protected T cast(RestfulRequest req) {
        Object target = req.getTarget();

        if (target == null)
            return null;

        return clazz.cast(target);
    }

}
