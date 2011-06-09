package com.bee32.plover.orm.ext.basic;

import java.io.Serializable;

import com.bee32.plover.orm.entity.Entity;

public abstract class CreateHandler<E extends Entity<K>, K extends Serializable>
        extends CreateOrEditHandler<E, K> {

    @Override
    public EntityActionResult handleRequest(EntityActionRequest req, EntityActionResult result)
            throws Exception {
        result.put("method", "create");
        result.put("METHOD", result.V.get("create"));

        super.handleRequest(req, result);

        return result.sendRedirect("index.htm");
    }

}
