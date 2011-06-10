package com.bee32.plover.orm.ext.basic;

import java.io.Serializable;

import com.bee32.plover.orm.entity.Entity;

public class EditHandler<E extends Entity<K>, K extends Serializable>
        extends CreateOrEditHandler<E, K> {

    public EditHandler(IEntityForming<E, K> forming) {
        super(forming);
    }

    @Override
    public EntityActionResult handleRequest(EntityActionRequest req, EntityActionResult result)
            throws Exception {

        req.setMethod("edit");

        result.setViewName(normalizeView("index"));
        result.put("METHOD", result.V.get("edit"));

        super.handleRequest(req, result);

        // TODO normalizeURL??
        return result.sendRedirect("index.htm");
    }

}
