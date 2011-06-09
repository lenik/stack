package com.bee32.plover.orm.ext.basic;

import java.io.Serializable;

import com.bee32.plover.orm.entity.Entity;

public abstract class EditFormHandler<E extends Entity<K>, K extends Serializable>
        extends CreateOrEditHandler<E, K> {

    @Override
    public EntityActionResult handleRequest(EntityActionRequest req, EntityActionResult result)
            throws Exception {

        result.setViewName(normalizeView("form"));
        result.put("method", "edit");
        result.put("METHOD", result.V.get("edit"));

        return super.handleRequest(req, result);
    }

}
