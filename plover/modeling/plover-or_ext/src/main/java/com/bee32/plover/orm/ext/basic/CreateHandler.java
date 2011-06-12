package com.bee32.plover.orm.ext.basic;

import java.io.Serializable;

import com.bee32.plover.orm.entity.Entity;

public class CreateHandler<E extends Entity<K>, K extends Serializable>
        extends CreateOrEditHandler<E, K> {

    public CreateHandler(IEntityForming<E, K> forming) {
        super(forming);
    }

    @Override
    public EntityActionResult handleRequest(EntityActionRequest req, EntityActionResult result)
            throws Exception {
        result.put("method", "create");
        result.put("METHOD", result.V.get("create"));

        super.handleRequest(req, result);

        return result.sendRedirect("index.do");
    }

}
