package com.bee32.plover.orm.ext.basic;

import java.io.Serializable;

import com.bee32.plover.orm.entity.Entity;

public abstract class CreateFormHandler<E extends Entity<K>, K extends Serializable>
        extends CreateOrEditHandler<E, K> {

    @Override
    public EntityActionResult handleRequest(EntityActionRequest req, EntityActionResult result)
            throws Exception {
        result.setViewName(normalizeView("form"));
        result.put("method", "create");
        result.put("METHOD", result.V.get("create"));

        return super.handleRequest(req, result);
    }

}
